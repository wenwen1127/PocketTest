package com.pkt.TestExecution;

import com.pkt.Common.constant.CommonConstant;
import com.pkt.Common.constant.KeywordConstant;
import com.pkt.Common.convert.PyscriptConvert;
import com.pkt.Common.convert.SuiteConvert;
import com.pkt.Config.SpringUtils;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.Keyword.PyscriptService;
import com.pkt.Service.TestProject.TestSuiteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;


@Component
public class ScriptDispatch {

//    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
//    private TestSuiteService testSuiteService = applicationContext.getBean(TestSuiteService.class);
////    private ApplicationContext applicationContext1 = SpringUtils.getApplicationContext();
//
//    private PyscriptService pyscriptService = applicationContext.getBean(PyscriptService.class);


    @Autowired
    private TestSuiteService testSuiteService;

    @Autowired
    private PyscriptService pyscriptService;

    public static ScriptDispatch scriptDispatch;

    @PostConstruct
    public void init() {
        scriptDispatch = this;
        scriptDispatch.pyscriptService = this.pyscriptService;
        scriptDispatch.testSuiteService = this.testSuiteService;
    }

    /**
     *  通过keyword查找对应的函数
     *  脚本的调度顺序 ：
     *      1.当前suite的keywords部分  2.内置的keyword  3.隶属于script的keyword  4.隶属于module的keyword
     *      5.隶属于TestProject的keyword   6.隶属于全局的keyword
     * @param suite_id
     * @param keyword
     */
    public Map<String, Object> dispatchPyscript(long suite_id, String keyword) {
        System.out.println(scriptDispatch.testSuiteService.getBySuiteId(suite_id));
        String filepath = scriptDispatch.testSuiteService.getBySuiteId(suite_id).get("file_path").toString();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            String suiteContent = FileHandler.readFile(filepath);
            Set<String> suiteKeyword =  SuiteConvert.separateKeyword(suiteContent).keySet();
            if(suiteKeyword.contains(keyword)){
                returnMap.put("content", SuiteConvert.separateKeyword(suiteContent).get(keyword));
                return returnMap;
            }
            if(Arrays.asList(KeywordConstant.BUILD_IN_KEYWORD).contains(keyword)){
                returnMap.put("script_path", CommonConstant.BUILDIN_KEYWORD_PATH);
                return returnMap;
            }

            Map<String, Object> suite_info = scriptDispatch.testSuiteService.getBySuiteId(suite_id);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("suite_id", suite_id);
            List<Map<String, Object>> script_in_suite = scriptDispatch.pyscriptService.getPyscriptByParams(params);


            for(Map<String, Object> scriptInfo : script_in_suite){
                String file_path = scriptInfo.get("file_path").toString();
                String pyscriptContent = FileHandler.readFile(file_path);
                List<String> keyword_name = PyscriptConvert.separatePyscript(pyscriptContent);
                System.out.println("keyword_name: " + keyword_name+ "----"+ keyword); //打印结果为空
                if(keyword_name.contains(keyword)){
                    returnMap.put("script_path", file_path);
                    System.out.println(returnMap);
                    return returnMap;
                }
            }

            if(suite_info.containsKey("testmodule_id")){
                long testmodule_id = Long.valueOf(suite_info.get("testmodule_id").toString());
                params.clear();
                params.put("testmodule_id", testmodule_id);
                List<Map<String, Object>> script_in_module = scriptDispatch.pyscriptService.getPyscriptByParams(params);
                for(Map<String, Object> scriptInfo : script_in_module){
                    String file_path = scriptInfo.get("file_path").toString();
                    String pyscriptContent = FileHandler.readFile(filepath);
                    List<String> keyword_name = PyscriptConvert.separatePyscript(pyscriptContent);
                    if(keyword_name.contains(keyword)){
                        returnMap.put("script_path", file_path);
                        return returnMap;
                    }
                }
            }
            long testproject_id = Long.valueOf(suite_info.get("testproject_id").toString());
            params.clear();
            params.put("testproject_id", testproject_id);
            List<Map<String, Object>> script_in_project = scriptDispatch.pyscriptService.getPyscriptByParams(params);
            for(Map<String, Object> scriptInfo : script_in_project){
                String file_path = scriptInfo.get("file_path").toString();
                String pyscriptContent = FileHandler.readFile(filepath);
                List<String> keyword_name = PyscriptConvert.separatePyscript(pyscriptContent);
                if(keyword_name.contains(keyword)){
                    returnMap.put("script_path", file_path);
                    return returnMap;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return returnMap;
    }

    public static void main(String[] args) {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("suite_id", 1);
//        System.out.println(testSuiteService.getBySuiteId(3));
    }


}
