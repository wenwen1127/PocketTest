package com.pkt.Controller.TestProject;

import com.pkt.Common.Exception.KeywordNotFoundException;
import com.pkt.Common.Exception.NumberOfProvidedValuesException;
import com.pkt.Common.Exception.TypeConvertException;
import com.pkt.Common.convert.SuiteConvert;
import com.pkt.Common.relation.SuiteCase;
import com.pkt.Handler.CommonHandler;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.TestProject.TestCaseService;
import com.pkt.Service.TestProject.TestSuiteService;
import com.pkt.TestExecution.RunCaseUtil;
import com.pkt.TestExecution.SetEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test/testproject/case")
public class TestCaseController {
    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    TestSuiteService testSuiteService;
    @Autowired
    RunCaseUtil runCaseUtil;
    @Resource
    private CommonHandler handler;
    @Resource
    private SuiteCase suiteCase;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getByCaseId(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        Map<String, Object> caseInfo = testCaseService.getByCaseId(Integer.valueOf(params.get("case_id").toString()));
        if(caseInfo.size()>0){
            params.putAll(caseInfo);
            params.put("success",true);
            params.put("msg","获取测试用例信息成功！");
        }else {
            params.put("success",false);
            params.put("msg","获取测试用例信息失败");
        }
        return res;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ModelAndView deleteByCaseId(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/delete");
        try {
            int case_id = Integer.valueOf(params.get("case_id").toString());
            Map<String, Object> caseInfo = testCaseService.getByCaseId(case_id);
            if (caseInfo!= null && caseInfo.size()>0){
                System.out.println(testCaseService.getSuiteInfoByCaseId(case_id));
                String suitepath = testCaseService.getSuiteInfoByCaseId(case_id).get("file_path").toString();
                long suite_id = Long.valueOf(testCaseService.getSuiteInfoByCaseId(case_id).get("suite_id").toString());

                try {
                    String suiteContent = FileHandler.readFile(suitepath);
                    List<String> caseNames = new ArrayList<String>();
                    caseNames.add(caseInfo.get("case_name").toString());

                    String modify_time = suiteCase.SuiteFileUpdateDuetoCaseDelete(caseNames,suitepath).toString();
                    Map<String, Object> suiteparms = new HashMap<String, Object>();
                    suiteparms.put("suite_id",suite_id);
                    suiteparms.put("modify_time", modify_time);

                    int suite_code = testSuiteService.editTestSuite(suiteparms);
                    int code = testCaseService.deleteByCaseId(case_id);
                    if(code==1 && suite_code==1){
                        params.put("code", code);
                        params.put("success", true);
                        params.put("msg", "删除测试用例信息成功");
                    } else {
                        params.put("success", false);
                        params.put("msg", "删除测试用例失败");
                    }
                }catch (IOException e2){
                    e2.printStackTrace();
                }
            }else{
                params.put("success", false);
                params.put("msg", "删除测试用例失败，测试用例不存在");
            }
        }catch(Exception e){
            e.printStackTrace();
            params.put("success", false);
            params.put("msg", "服务器异常");
        }
        return res;
    }

    @RequestMapping("/getcontent")
    @ResponseBody
    public ModelAndView getCaseContent(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/getcontent");
        try {
            long case_id = Long.valueOf(params.get("case_id").toString());
            String case_name = params.get("case_name").toString();
            String suitepath = testCaseService.getSuiteInfoByCaseId(case_id).get("file_path").toString();
            long suite_id = Long.valueOf(testCaseService.getSuiteInfoByCaseId(case_id).get("suite_id").toString());
            try {
                String suiteContent = FileHandler.readFile(suitepath);
                String caseContent = SuiteConvert.getCaseContent(case_name,suiteContent);
                params.put("content",caseContent);
                params.put("success", true);
                params.put("msg", "获取case内容成功");
            }catch (IOException e2){
                e2.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return res;
    }

    @RequestMapping("/querypagelist")
    @ResponseBody
    public Map queryPageList(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        try {
            System.out.println(params);
            List<Map<String,Object>> caseInfoList = testCaseService.queryPageList(params);
            System.out.println(caseInfoList);
            params.put("caseInfolist", caseInfoList);
            params.put("msg", "获取case分页信息成功");
            params.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return params;
    }

}
