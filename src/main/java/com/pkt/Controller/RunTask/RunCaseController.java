package com.pkt.Controller.RunTask;

import com.pkt.Common.Exception.TypeConvertException;
import com.pkt.Common.convert.SuiteConvert;
import com.pkt.Handler.CommonHandler;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.RunTask.RunCaseService;
import com.pkt.Service.TestProject.TestCaseService;
import com.pkt.Service.TestProject.TestSuiteService;
import com.pkt.TestExecution.RunCaseUtil;
import com.pkt.TestExecution.SetEnvironment;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/test/task/case")
public class RunCaseController {
    @Autowired
    private TestSuiteService testSuiteService;
    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    private RunCaseService runCaseService;
    @Resource
    private CommonHandler handler;
    @Resource
    private RunCaseUtil runCaseUtil;

    @RequestMapping("/run")
    @ResponseBody
    public ModelAndView RunCaseController(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/run");
        System.out.println("********* run case start ********");
        long case_id = Long.valueOf(params.get("case_id").toString());
        long suite_id = Long.valueOf(testCaseService.getByCaseId(case_id).get("suite_id").toString());
        params.put("suite_id", suite_id);
        String file_path = testSuiteService.getBySuiteId(suite_id).get("file_path").toString();
        try {
            String suiteContent = FileHandler.readFile(file_path);
            Map<String, Object> globalVar = SetEnvironment.getGlobalVariable(suiteContent);
            Date caseStartDate = DateUtil.now();
            params.put("caseStartDate", caseStartDate);
            if (SetEnvironment.runSuiteEnviroment(suiteContent).containsKey(2)) {
                Map<String, Object> setupReturn = runCaseUtil.runCaseSetup(suiteContent, globalVar, suite_id);
                params.put("setupReturn", setupReturn);
                if (setupReturn.get("result").equals("FAIL")) {
                    params.put("result", "FAIL");
                    params.put("caseTeardown", runCaseUtil.runCaseTeardown(suiteContent, globalVar,suite_id));
                    params.put("caseEndDate",DateUtil.now());
                    runCaseService.addRunCaseInfo(params);
                    testCaseService.updateCaseResult(params);
                    return res;
                }
            }
            String caseName = params.get("case_name").toString();
            String caseContent = SuiteConvert.getCaseContent(caseName, suiteContent);
            Map<String, Object> caseReturn = runCaseUtil.runCase(caseContent, globalVar, suite_id);
            caseReturn.put("caseName", caseName);
            params.put("caseReturn", caseReturn);
            if (caseReturn.get("result").equals("FAIL")) {
                params.put("caseTeardown", runCaseUtil.runCaseTeardown(suiteContent, globalVar,suite_id));
                params.put("result", "FAIL");
                params.put("caseEndDate",DateUtil.now());
                runCaseService.addRunCaseInfo(params);
                testCaseService.updateCaseResult(params);
                return res;
            }
            if (SetEnvironment.runSuiteEnviroment(suiteContent).containsKey(-2)) {
                Map<String, Object> teardownReturn = runCaseUtil.runCaseTeardown(suiteContent, globalVar, suite_id);
                params.put("teardownReturn", teardownReturn);
                if (teardownReturn.get("result").equals("FAIL")) {
                    params.put("result", "FAIL");
                    params.put("caseEndDate",DateUtil.now());
                    runCaseService.addRunCaseInfo(params);
                    testCaseService.updateCaseResult(params);
                    return res;
                }
            }
            params.put("result", "PASS");
            params.put("caseEndDate",DateUtil.now());
            runCaseService.addRunCaseInfo(params);
            testCaseService.updateCaseResult(params);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            params.put("result", "FAIL");
            params.put("caseEndDate",DateUtil.now());
            testCaseService.updateCaseResult(params);
            return res;
        } catch (TypeConvertException e1) {
            e1.printStackTrace();
            params.put("caseEndDate",DateUtil.now());
            params.put("result", "FAIL");
            testCaseService.updateCaseResult(params);
            return res;
        }
    }
}
