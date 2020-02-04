package com.pkt.Common.convert;

import com.pkt.Common.constant.CommonConstant;
import com.pkt.Common.constant.SuiteConstant;
import com.pkt.Entity.TestProject.TestCase;
import com.pkt.Handler.FileHandler;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuiteConvert {
    /**
     * 将suite分成多个模块
     * 返回Test Cases|Settings|Variables|Keywords 部分及其内容
     * @param suiteContent
     * @return
     */
    public static Map<String, String> separateSuite(String suiteContent){
        Map<String, String> suitepartmap = new HashMap<String, String>();

        String pattern = "\\*{3}\\s(Test Cases|Settings|Variables|Keywords+)\\s\\*{3}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(suiteContent);
        Map<String, Integer> indexMap = new LinkedHashMap<String, Integer>();
        while (m.find()) {
            indexMap.put(m.group(),m.start());
        }
//            System.out.println(indexMap);

        Iterator <Map.Entry<String,Integer>> entries = indexMap.entrySet().iterator();
        if(entries.hasNext()) {
            Map.Entry<String, Integer> entry = entries.next();
            String key = entry.getKey();
            Integer value = entry.getValue();
            while (entries.hasNext()) {
                entry = entries.next();
                suitepartmap.put(key, suiteContent.substring(value, entry.getValue()));
                key = entry.getKey();
                value = entry.getValue();
            }
            suitepartmap.put(key,suiteContent.substring(value));
        }
        return suitepartmap;
    }

    /**
     * 从suite中取出case
     * @param suiteContent
     * @return
     */
    public static List<List<String>> separateCase(String suiteContent) {
        List<List<String>> caseList = new ArrayList<List<String>>();
        String casePart = separateSuite(suiteContent).get(SuiteConstant.CASE_TITLE);
        if(casePart!=null){
            List<String> caselines = new ArrayList<String>();
            for(String line : Arrays.asList(casePart.split("\n"))){
                if(!line.equals("")){
                    caselines.add(line);
                }
            }
            String pattern = "^\\s{0}\\S.*";
            int count = 0;
            String caseName=null;
            StringBuilder caseContent = new StringBuilder();
            for(String caseline : caselines) {
                if (caseline.equals(SuiteConstant.CASE_TITLE)){
                    continue;
                }
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(caseline);
                try {
                    if (m.find()) {
                        if (count != 0) {
                            TestCase testCase = new TestCase(caseName, caseContent.toString());
                            caseList.add(testCase.toStringList());
                        }
                        caseName = m.group();
                        caseContent = new StringBuilder();
                        count++;
//                        System.out.println(m.group());
                    } else {
                        try {
                            caseContent.append(caseline + "\n");
                        } catch (NullPointerException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (caseline ==(caselines.get(caselines.size() - 1))) {
                        TestCase testCase = new TestCase(caseName, caseContent.toString());
                        caseList.add(testCase.toStringList());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return caseList;
    }


    public static Map<String, String> separateSetting(String suiteContent) {
        Map<String, String> settingMap = new HashMap<String, String>();
        String settingPart = separateSuite(suiteContent).get(SuiteConstant.SETTING_TITLE);
        String[] settings = settingPart.split("\n");
        for(String setline : settings ){
            if(!setline.equals("")){
                if(setline.split("\\s{2,}").length == 2){
                    String setKeyword = setline.split("\\s{2,}")[0];
                    String setName = setline.split("\\s{2,}")[1];
                    settingMap.put(setKeyword, setName);
                }
            }
        }
        return settingMap;
    }

    /**
     * 分离keywords部分
     * 返回 keyword_name keyword_content
     * @param suiteContent
     * @return
     */
    public static Map<String, String> separateKeyword(String suiteContent) {
        Map<String, String> keywordMap = new HashMap<String, String>();
        String keywordPart = separateSuite(suiteContent).get(SuiteConstant.KEYWORD_TITLE);
        List<String> keywordlines = Arrays.asList(keywordPart.split("\n"));
        List<String> keywords = new ArrayList<String>();
        String pattern = "^\\s{0}\\S.*";

        for(String keywordline : keywordlines){
            if(!keywordline.equals("") && !keywordline.equals(SuiteConstant.KEYWORD_TITLE)){
                keywords.add(keywordline);
            }
        }
        int count = 0;
        String keywordName = null;
        StringBuilder keywordContent=null;
        for(String keyword : keywords){
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(keyword);
            if (m.find()) {
                if (count != 0) {
                    keywordMap.put(keywordName,keywordContent.toString());
                }
                keywordName = m.group();
                keywordContent = new StringBuilder();
                count++;
            } else {
                try {
                    keywordContent.append(keyword + "\n");
                } catch (NullPointerException e2) {
                    e2.printStackTrace();
                }
            }
            if (keyword == (keywords.get(keywords.size() - 1))) {
                keywordMap.put(keywordName, keywordContent.toString());
            }
        }
        return keywordMap;
    }

    public static Map<String, String> separateVariable(String suiteContent){
        Map<String, String> variableMap = new HashMap<String, String>();
        String variablePart = separateSuite(suiteContent).get(SuiteConstant.VARIABLE_TITLE);
        List<String> variables = Arrays.asList(variablePart.split("\n"));
        for(String varline : variables ){
            if(!varline.equals("") && !varline.equals(SuiteConstant.VARIABLE_TITLE)){
                List<String> key_value = Arrays.asList(varline.split("\\s{2,}"));
//                System.out.println(key_value);
                if(key_value.size() == 2){
                    String varKey = key_value.get(0);
                    String varValue = key_value.get(1);
                    variableMap.put(varKey, varValue);
                }
                if(key_value.size() == 1){
                    String varKey = key_value.get(0);
                    String varValue = "";
                    variableMap.put(varKey, varValue);
                }
            }
        }
        return variableMap;
    }

    public static String getCaseContent(String caseName, String suiteContent){
        String caseContent = "";
        List<List<String>> caseList = SuiteConvert.separateCase(suiteContent);
        for(List<String> caselst : caseList){
            if(caseName.trim().equals(caselst.get(0).trim())){
                caseContent = caselst.get(1);
                break;
            }
        }
        return caseContent;
    }



    public static void main(String[] args) {
//        System.out.println(SuiteConvert.separateSuite("/Users/wuwenwen/Documents/Homework/GraduationDesign/MaterialLibrary/BasicFile.txt"));
//        List<TestCase> result = SuiteConvert.separateCase("/Users/wuwenwen/Documents/Homework/GraduationDesign/MaterialLibrary/BasicFile.txt");
//        for(TestCase testcase: result){
//            testcase.toString(testcase);
//        }
        try {
            String suiteContent = FileHandler.readFile("/Users/wuwenwen/Documents/Homework/GraduationDesign/MaterialLibrary/BasicFile.txt");
            System.out.println(separateKeyword(suiteContent));
//            System.out.println(separateVariable(suiteContent));
//            System.out.println(separateSetting(suiteContent));
        }catch (IOException e){
            e.printStackTrace();
        }


    }

}
