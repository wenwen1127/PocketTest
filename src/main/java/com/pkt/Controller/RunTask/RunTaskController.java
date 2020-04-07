package com.pkt.Controller.RunTask;

import com.alibaba.druid.support.json.JSONUtils;
import com.pkt.Common.utils.DateUtil;
import com.pkt.Common.utils.Test;
import com.pkt.GeneticAlgorithm.MainRun;
import com.pkt.GeneticAlgorithm.TSPData;
import com.pkt.Handler.CommonHandler;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.RunTask.RunCaseService;
import com.pkt.Service.RunTask.RunSuiteService;
import com.pkt.Service.TestProject.TestCaseService;
import com.pkt.Service.TestProject.TestSuiteService;
import com.pkt.TestExecution.RunTaskUtil;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.DateUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/test/task")
public class RunTaskController {
    @Autowired
    private TestSuiteService testSuiteService;
    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    private RunCaseService runCaseService;
    @Autowired RunCaseController runCaseController;
    @Resource
    private CommonHandler handler;
    @Resource
    private RunTaskUtil runTaskUtil;
    @Autowired
    private RunSuiteService runSuiteService;

//    @RequestMapping("/run")
//    @ResponseBody
//    public Map RunTaskController(HttpServletRequest request) {
//        Map<String, Object> params = handler.getParams(request);
//        System.out.println("请求数据： "+ DateUtil.now() +"  "+ params);
//        List<Map<String, Object>> runCaseList = SortCaseList(params);
//        List<Map> returnList = new ArrayList<>();
//        for(Map<String, Object> runInfo : runCaseList){
//            runInfo.put("section_id", params.get("section_id"));
//            runInfo.put("user_id", params.get("user_id"));
//            Map returnMap = runCaseController.runCase(runInfo);
//            returnList.add(returnMap);
//        }
//        params.put("returnList", returnList);
//        params.put("success", true);
//        return params;
//
//    }
    @RequestMapping("/get")
    @ResponseBody
    public Map GetTaskInfoController(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        System.out.println("查询数据： " + params);
        String startDate = params.get("startDate").toString();
        List<Map<String, Object>> completedList = runCaseService.completedTask(startDate);
        if(completedList.size()>0){
            String latestEndDate = completedList.get(completedList.size()-1).get("case_end_date").toString();
            params.put("endDate", latestEndDate);
        }
        params.put("completedList", completedList);
        params.put("success", true);
        return params;
    }

//    @RequestMapping("/getlist")
//    @ResponseBody
//    public Map GetTaskListController(HttpServletRequest request) {
//        Map<String, Object> params = handler.getParams(request);
//        List<Map<String, Object>> runCaseList = SortCaseList(params);
//        List<Map<String, Object>> taskCaseList = new ArrayList<>();
//        Map<String, Object> caseList = (Map<String, Object>)JSONUtils.parse(params.get("caseList").toString());
//        for (Object caselst : caseList.values()) {
//            List<Map<String, Object>> lst =  (List<Map<String, Object>>)caselst;
//            taskCaseList.addAll(lst);
//        }
//        if (runCaseList.size()>0){
//            params.put("taskCaseList", taskCaseList);
//            params.put("runCaseList", runCaseList);
//            params.put("success", true);
//        }
//        return params;
//    }
    @RequestMapping("/run")
    @ResponseBody
    public Map BatchRunController(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        Map<String, List> lstMap = testSuiteService.batchRunSuite((List<Map>) JSONUtils.parse(params.get("suiteList").toString()));
        TSPData.suiteList = lstMap.get("suiteList");
        TSPData.pointList = lstMap.get("pointList");
        TSPData.SUITE_NUM = TSPData.suiteList.size();
        TSPData.POINT_NUM = TSPData.pointList.size();
        System.out.println(lstMap);
//        Test.test(TSPData.pointList,TSPData.suiteList,TSPData.SUITE_NUM,TSPData.POINT_NUM);

        String[] res = MainRun.main();
        List<Map> resList = new ArrayList<>();
        for(String index : res){
            Map suiteInfo = TSPData.suiteList.get(Integer.valueOf(index));
            System.out.println("********* suite start ********");
            suiteInfo.put("section_id", params.get("section_id"));
            suiteInfo.put("user_id", params.get("user_id"));
            long suite_id = Long.valueOf(suiteInfo.get("suite_id").toString());
            String file_path = testSuiteService.getBySuiteId(suite_id).get("file_path").toString();
            Map<String, Object> result = runTaskUtil.runSuite(suite_id, file_path,suiteInfo);
            suiteInfo.putAll(result);
            System.out.println(suiteInfo);
            params.put("suite_result", result);
            runSuiteService.addRunSuiteInfo(suiteInfo);

            resList.add(suiteInfo);
        }
        System.out.println("返回值："+resList);
        params.put("returnList", resList);
        params.put("success", true);
        Long StartDate = DateUtil.getTimestamp(((Map)(params.get("suite_result"))).get("suiteStartDate").toString());
        try {
            FileHandler.createFile("/Users/wuwenwen/Documents/Homework/GraduationDesign/MaterialLibrary/test/Json", StartDate+".json");
            FileHandler.modifyFile("/Users/wuwenwen/Documents/Homework/GraduationDesign/MaterialLibrary/test/Json/"+StartDate+".json", JSONUtils.toJSONString(params));
        }catch (IOException e){
            e.printStackTrace();
        }




        return params;
    }



//    public List<Map<String, Object>> SortCaseList(Map<String, Object> params){
//        List<Map> suiteList = (List<Map>) JSONUtils.parse(params.get("suiteList").toString());
//        Map<String, Object> caseList = (Map<String, Object>)JSONUtils.parse(params.get("caseList").toString());
//        System.out.println("suiteList: " + suiteList);
//        System.out.println("caseList: " + caseList);
//        List<Map<String, Object>> runCaseList = new ArrayList<Map<String, Object>>();
//        for (Object caselst : caseList.values()) {
//            List<Map<String, Object>> lst =  (List<Map<String, Object>>)caselst;
//            runCaseList.addAll(lst);
//        }
//        Map<String, Object> para = new HashMap<>();
//        for(Map suitelst : suiteList){
//            para.put("suite_id", suitelst.get("suite_id"));
//            runCaseList.addAll(testCaseService.queryPageList(para));
//        }
//        return runCaseList;
//    }

    @RequestMapping("/querypagelist")
    @ResponseBody
    public Map queryPageListController(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        List<Map<String, Object>> runCaseList = runCaseService.queryPageList(params);
        params.put("runTaskList",runCaseList);
        params.put("success", true);
        return params;
    }

}
