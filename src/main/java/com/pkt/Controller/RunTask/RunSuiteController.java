package com.pkt.Controller.RunTask;

import com.pkt.Handler.CommonHandler;
import com.pkt.Service.TestProject.TestSuiteService;
import com.pkt.TestExecution.RunCaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/test/task/suite")
public class RunSuiteController {

    @Autowired
    private TestSuiteService testSuiteService;
    @Resource
    private CommonHandler handler;
    @Resource
    private RunCaseUtil runCaseUtil;

    @RequestMapping("/run")
    @ResponseBody
    public ModelAndView getBySuiteId(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/run");
        System.out.println("********* suite start ********");
        long suite_id = Long.valueOf(params.get("suite_id").toString());
        String file_path = testSuiteService.getBySuiteId(suite_id).get("file_path").toString();
        Map<String, Object> result = runCaseUtil.runSuite(suite_id, file_path,params);
        params.put("suite_result", result);
        return res;
    }

}
