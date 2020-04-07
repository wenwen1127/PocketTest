package com.pkt.Controller.RunTask;

import com.pkt.Handler.CommonHandler;
import com.pkt.Service.RunTask.RunSuiteService;
import com.pkt.Service.TestProject.TestSuiteService;
import com.pkt.TestExecution.RunTaskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test/task/suite")
public class RunSuiteController {

    @Autowired
    private TestSuiteService testSuiteService;
    @Autowired
    private RunSuiteService runSuiteService;
    @Resource
    private CommonHandler handler;
    @Resource
    private RunTaskUtil runTaskUtil;


    @RequestMapping("/run")
    @ResponseBody
    public Map runSuite(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        System.out.println("********* suite start ********");
        long suite_id = Long.valueOf(params.get("suite_id").toString());
        String file_path = testSuiteService.getBySuiteId(suite_id).get("file_path").toString();
        Map<String, Object> result = runTaskUtil.runSuite(suite_id, file_path,params);
        params.putAll(result);
        System.out.println(params);
        runSuiteService.addRunSuiteInfo(params);
        params.put("suite_result", result);
        if(result.get("result").equals("PASS")){
            params.put("success", true);
        }else {
            params.put("success", false);
        }
        return params;
    }

    @RequestMapping("/query")
    @ResponseBody
    public Map querySuite(HttpServletRequest request) {
        Map params = handler.getParams(request);
        List<Map> runSuiteList = runSuiteService.getRunSuiteInfoByParams(params);
        params.put("runSuiteList",runSuiteList);
        params.put("success", true);
        return params;
    }

}
