package com.pkt.TestExecution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkt.Common.Exception.KeywordNotFoundException;
import com.pkt.Common.Exception.NumberOfProvidedValuesException;
import com.pkt.Common.Exception.TypeConvertException;
import com.pkt.Common.constant.CommonConstant;
import com.pkt.Common.convert.CaseConvert;
import com.pkt.Common.convert.Format;
import com.pkt.Common.convert.SuiteConvert;
import com.pkt.Common.utils.RunCmdUtils;
import com.pkt.Entity.TestProject.TestCase;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.RunTask.RunCaseService;
import com.pkt.Service.TestProject.TestCaseService;
import com.pkt.Service.TestProject.TestSuiteService;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.Normalizer;
import java.util.*;

@Component
public class RunCaseUtil {

    @Autowired
    ScriptDispatch scriptDispatch;
    @Autowired
    TestSuiteService testSuiteService;
    @Autowired
    TestCaseService testCaseService;
    @Autowired
    RunCaseService runCaseService;

    public RunCaseUtil runCaseUtil;

    @PostConstruct
    public void init() {
        runCaseUtil = this;
        runCaseUtil.testSuiteService = this.testSuiteService;
        runCaseUtil.testCaseService = this.testCaseService;
        runCaseUtil.runCaseService = this.runCaseService;
    }

//  执行case 将返回结果赋值给returnlist中的数 在将varName value组成的map加入到varList中
//  List实时的存放数据 varname相同的则更新 不同的则添加
    public Map<String, Object> runSentence(Map<String, Object> globalVar, Map<String, Object> sentenceMap, long suite_id) throws KeywordNotFoundException,NumberOfProvidedValuesException {
        String keyword = sentenceMap.get("keyword").toString();
        List<String> returnList = (List<String>) sentenceMap.get("returnList");
        List<String> inputList = (List<String>) sentenceMap.get("inputList");
        Map<String, Object> returnMap = new HashMap<String, Object>();

        List inputValue = new ArrayList();
        List<String> outputName = new ArrayList<String>();
        if(inputList != null){
            for(String inputName : inputList){
                try {
                    inputValue.add(Format.getValue(inputName, globalVar));
                }catch (TypeConvertException e){
                    e.printStackTrace();
                }
            }
        }
        if(returnList != null){
//            System.out.println(returnList);
            for(String returnName : returnList){
                try {
                    String type = Format.getTypeName(returnName);
                    String valueName = Format.convert_value_name(returnName).get(type);
                    outputName.add(valueName);
                }catch (TypeConvertException e){
                    e.printStackTrace();
                }
            }
        }

        Map<String, Object> parseKeyword = scriptDispatch.dispatchPyscript(suite_id, keyword);
        System.out.println("parseKeyword"+parseKeyword);
        if(keyword.equals("")){
            return returnMap;
        }else if(parseKeyword==null || parseKeyword.size()==0){
            throw new KeywordNotFoundException("keyword " + keyword + " Not Found!!");
        }else {
            if(parseKeyword.containsKey("script_path")){
                String script_path = parseKeyword.get("script_path").toString();

                String script_dir = script_path.substring(0,script_path.lastIndexOf(CommonConstant.FILE_SEPARATOR)+1);
                String script_name = script_path.substring(script_path.lastIndexOf(CommonConstant.FILE_SEPARATOR)+1, script_path.length()).split("\\.")[0];

                String scr_path = CommonConstant.GET_RETURN_PATH;
                String dst_path = script_dir + "getReturn.py";

                if(!FileHandler.exist_file_in_folder("getReturn.py",script_dir)){
                    try {
                        FileHandler.CopyFile(scr_path, dst_path);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

                StringBuilder sb_param = new StringBuilder();
                for(Object input: inputValue){
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.convertValue(input, input.getClass());
                    if(input != inputValue.get(inputValue.size()-1)){
                        sb_param.append(input+",");
                    }else {
                        sb_param.append(input);
                    }
                }
                List<String> return_value = GetSentenceReturn.getSentReturn(script_name,script_dir,keyword,sb_param.toString());

//                System.out.println(outputName);
                if(return_value.size() == outputName.size()){
                    for(int i = 0;i<return_value.size();i++){
                        returnMap.put(outputName.get(i), return_value.get(i));
                    }
                }else {
                    throw new NumberOfProvidedValuesException("the number of provided valueNames  not match the values return");
                }
            }
        }
        System.out.println("sentenceReturnMap" + returnMap);
        return returnMap;
    }


    /**
     * 当keyword为keywords中的关键字时
     * 递归调用runcase
     * @param caseContent
     * @param globalVar
     * @param suite_id
     * @return
     */
    public Map<String, Object> runCase(String caseContent, Map<String, Object> globalVar,long suite_id){
        List<Map<String, Object>> sentenList = CaseConvert.separeteCaseSentence(caseContent);
        Map<String, Object> runCaseMap = new HashMap<String, Object>();
        for(Map<String, Object> sentenMap : sentenList){
            try {
                Map<String, Object> parseKeyword = scriptDispatch.dispatchPyscript(suite_id, sentenMap.get("keyword").toString());
                if(parseKeyword.containsKey("content")){
                    String keywordsContent = parseKeyword.get("content").toString();
                    runCase(keywordsContent,globalVar,suite_id);
                }
                Map<String, Object> caseReturn = runSentence(globalVar, sentenMap, suite_id);
                globalVar.putAll(caseReturn);
                System.out.println("caserunning:" + globalVar);
            }catch (KeywordNotFoundException e1){
                e1.printStackTrace();
                runCaseMap.put("result", "FAIL");
                return runCaseMap;
            }catch (NumberOfProvidedValuesException e2){
                e2.printStackTrace();
                runCaseMap.put("result", "FAIL");
                return runCaseMap;
            }
        }
        runCaseMap.put("result", "PASS");
        return runCaseMap;
    }
    public  Map<String, Object> runSuite(long suite_id, String file_path, Map<String, Object> params){
        int pass_count = 0;
        int fail_count = 0;
        Map<String, Object> runSuiteMap = new HashMap<String, Object>();
        try {
            String suiteContent = FileHandler.readFile(file_path);
            Date suiteStartDate = DateUtil.now();
            runSuiteMap.put("suiteStartDate",suiteStartDate);
            List<Map> caseResultList = new ArrayList<Map>();
            Map<String, Object> globalVar = SetEnvironment.getGlobalVariable(suiteContent);
            List<List<String>> caseList = SuiteConvert.separateCase(suiteContent);
            if(SetEnvironment.runSuiteEnviroment(suiteContent).containsKey(1)){
                Map<String, Object> suiteSetupReturn = runSuiteSetup(suiteContent,globalVar,suite_id);
                runSuiteMap.put("suiteSetup", suiteSetupReturn);
                if(suiteSetupReturn.get("result").equals("FAIL")){
                    runSuiteMap.put("suiteTeardown", runSuiteTeardown(suiteContent,globalVar,suite_id));
                    runSuiteMap.put("result","FAIL");
                    runSuiteMap.put("suiteEndDate",DateUtil.now());
                    return runSuiteMap;
                }
            }
            Map<String, Object> runCaseInfo = new HashMap<>();
            for (List<String> caselst: caseList){
                Map<String, Object> caseResultMap = new HashMap<String, Object>();
                Date caseStartDate = DateUtil.now();
                caseResultMap.put("caseStartDate",caseStartDate);
                String caseName = caselst.get(0);
                String caseContent = caselst.get(1);
                long case_id = testCaseService.getCaseIdByParams(caseName, suite_id);
                caseResultMap.put("case_id", case_id);
                if (SetEnvironment.runSuiteEnviroment(suiteContent).containsKey(2)){
                    Map<String, Object> caseSetupReturn = runCaseSetup(suiteContent,globalVar,suite_id);
                    caseResultMap.put("caseSetupReturn", caseSetupReturn);
                    if(caseSetupReturn.get("result").equals("FAIL")){
                        runSuiteMap.put("caseTeardown", runCaseTeardown(suiteContent, globalVar,suite_id));
                        caseResultMap.put("result","FAIL");
                        caseResultMap.put("caseEndDate",DateUtil.now());
                        fail_count+=1;
                        runCaseInfo.putAll(params);
                        runCaseInfo.putAll(caseResultMap);
                        runCaseService.addRunCaseInfo(runCaseInfo);
                        testCaseService.updateCaseResult(runCaseInfo);
                        runCaseInfo.clear();
                        continue;
                    }
                }
                Map<String, Object> caseReturn = runCase(caseContent,globalVar,suite_id);
                caseReturn.put("caseName", caseName);
                caseResultMap.put("caseReturn", caseReturn);
                if(caseReturn.get("result").equals("FAIL")){
                    runSuiteMap.put("caseTeardown", runCaseTeardown(suiteContent, globalVar,suite_id));
                    caseResultMap.put("caseEndDate",DateUtil.now());
                    caseResultMap.put("result","FAIL");
                    runCaseInfo.putAll(params);
                    runCaseInfo.putAll(caseResultMap);
                    runCaseService.addRunCaseInfo(runCaseInfo);
                    testCaseService.updateCaseResult(runCaseInfo);
                    runCaseInfo.clear();
                    fail_count+=1;
                    continue;
                }

                if (SetEnvironment.runSuiteEnviroment(suiteContent).containsKey(-2)){
                    Map<String, Object> caseTeardownReturn =  runCaseTeardown(suiteContent, globalVar,suite_id);
                    caseResultMap.put("caseTeardownReturn", caseTeardownReturn);
                    if(caseTeardownReturn.get("result").equals("FAIL")){
                        caseResultMap.put("caseEndDate",DateUtil.now());
                        caseResultMap.put("result","FAIL");
                        fail_count+=1;
                        runCaseInfo.putAll(caseResultMap);
                        runCaseInfo.putAll(params);
                        testCaseService.updateCaseResult(runCaseInfo);
                        runCaseService.addRunCaseInfo(runCaseInfo);
                        runCaseInfo.clear();
                        continue;
                    }
                }
                caseResultMap.put("caseEndDate",DateUtil.now());
                caseResultMap.put("result","PASS");
                pass_count+=1;
                caseResultList.add(caseResultMap);
                runCaseInfo.putAll(params);
                runCaseInfo.putAll(caseResultMap);
                runCaseService.addRunCaseInfo(runCaseInfo);
                testCaseService.updateCaseResult(runCaseInfo);
                runCaseInfo.clear();
            }
            runSuiteMap.put("caseResultList",caseResultList);
            runSuiteMap.put("pass_count",pass_count);
            runSuiteMap.put("fail_count",fail_count);
            if(SetEnvironment.runSuiteEnviroment(suiteContent).containsKey(-1)){
                Map<String, Object> suiteTeardownReturn = runSuiteTeardown(suiteContent,globalVar,suite_id);
                runSuiteMap.put("suiteTeardownReturn",suiteTeardownReturn);
                if(suiteTeardownReturn.get("result").equals("FAIL")){
                    runSuiteMap.put("suiteEndDate",DateUtil.now());
                    runSuiteMap.put("result","FAIL");
                    return runSuiteMap;
                }
            }
            Date suiteEndDate = DateUtil.now();
            runSuiteMap.put("suiteEndDate",suiteEndDate);
            if(fail_count==0 && pass_count==caseList.size()){
                runSuiteMap.put("result","PASS");
            }else{
                runSuiteMap.put("result","FAIL");
            }
            return runSuiteMap;
        }catch (IOException e){
            e.printStackTrace();
            runSuiteMap.put("result","FAIL");
            runSuiteMap.put("suiteEndDate",DateUtil.now());
            return runSuiteMap;
        }catch (TypeConvertException e1){
            e1.printStackTrace();
            runSuiteMap.put("result","FAIL");
            runSuiteMap.put("suiteEndDate",DateUtil.now());
            return runSuiteMap;
        }
    }

    public  Map<String, Object> runSuiteSetup(String suiteContent, Map<String, Object> globalVar, long suite_id) {
        String suiteSetupContent = SetEnvironment.runSuiteEnviroment(suiteContent).get(1);
        Map<String, Object> suiteSetupReturn = runCase(suiteSetupContent,globalVar,suite_id);
        return suiteSetupReturn;
    }

    public  Map<String, Object> runSuiteTeardown(String suiteContent, Map<String, Object> globalVar,long suite_id) {
        String suiteTeardownContent = SetEnvironment.runSuiteEnviroment(suiteContent).get(-1);
        Map<String, Object> suiteTeardownReturn = runCase(suiteTeardownContent,globalVar,suite_id);
        return suiteTeardownReturn;
    }

    public  Map<String, Object> runCaseSetup(String suiteContent, Map<String, Object> globalVar,long suite_id) {
        String caseSetupContent = SetEnvironment.runSuiteEnviroment(suiteContent).get(2);
        Map<String, Object> caseSetupReturn = runCase(caseSetupContent,globalVar,suite_id);
        return caseSetupReturn;
    }

    public  Map<String, Object> runCaseTeardown(String suiteContent, Map<String, Object> globalVar,long suite_id) {
        String caseTeardownContent = SetEnvironment.runSuiteEnviroment(suiteContent).get(-2);
        Map<String, Object> caseTeardownReturn =  runCase(caseTeardownContent,globalVar,suite_id);
        return caseTeardownReturn;
    }


    public static void main(String[] args) {
//        try {
//            Map<String, Object> globalVar = new HashMap<String, Object>();
//            globalVar.put("str","123456789");
//            Map<String, Object> sentenceMap = new HashMap<String, Object>();
//            sentenceMap.put("keyword", "test");
//            List<String> returnList = new ArrayList<String>();
//            returnList.add("${buf}");
//            returnList.add("${len_str}");
//            List<String> inputList = new ArrayList<String>();
//            inputList.add("${str}");
//            sentenceMap.put("inputList",inputList);
//            sentenceMap.put("returnList",inputList);
//            runSentence(globalVar, sentenceMap, 3);
//        }catch (KeywordNotFoundException e){
//            e.printStackTrace();
//        }


    }


}
