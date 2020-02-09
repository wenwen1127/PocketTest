package com.pkt.Controller.TestProject;

import com.alibaba.druid.support.json.JSONUtils;
import com.pkt.Handler.CommonHandler;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.Keyword.PyscriptService;
import com.pkt.Service.TestProject.TestModuleService;
import com.pkt.Service.TestProject.TestProjectService;
import com.pkt.Service.TestProject.TestSuiteService;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/test/testproject")
public class TestProjectController {
    @Autowired
    private TestProjectService testProjectService;
    @Autowired
    private TestModuleService testModuleService;
    @Autowired
    private TestSuiteService testSuiteService;
    @Autowired
    private PyscriptService pyscriptService;
    @Resource
    private CommonHandler handler;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getByTestProjectId(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        Map<String, Object> projectInfo = testProjectService.getByTestProjectId(Integer.valueOf(params.get("testproject_id").toString()));
        if(projectInfo.size()>0){
            params.putAll(projectInfo);
            params.put("success",true);
            params.put("msg","获取测试项目信息成功！");
        }else {
            params.put("success",false);
            params.put("msg","获取测试项目信息失败");
        }
        return res;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ModelAndView addTestProject(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/add");
        System.out.println("增加测试项目信息" + params);
        try {
            Map<String, Object> folderInfo = new HashMap<String, Object>();
            String folderpath = params.get("folderpath").toString();
            String foldername = params.get("foldername").toString();
            try {
                folderInfo = FileHandler.createFolder(folderpath, foldername);
                params.put("create_time", folderInfo.get("create_time"));
                params.put("modify_time", folderInfo.get("modify_time"));
            }catch(IOException e2){
                e2.printStackTrace();
            }

            String testproject_path = folderpath + "/" + foldername;
            params.put("testproject_path", testproject_path);
            int code = testProjectService.addTestProject(params);
            params.put("code", code);
            params.put("success", true);
            params.put("msg", "添加测试项目信息成功");

        }catch(Exception e){
            e.printStackTrace();
            params.put("success", false);
            params.put("msg", "服务器异常");
        }
        return res;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ModelAndView deleteByTestProjectId(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/delete");
        try {
            int testproject_id = Integer.valueOf(params.get("testproject_id").toString());
            Map<String, Object> testProjectInfo = testProjectService.getByTestProjectId(testproject_id);
            if (testProjectInfo != null && testProjectInfo.size()>0){
                String testproject_path = testProjectInfo.get("testproject_path").toString();
                try {
                    if(FileHandler.deleteFile(testproject_path)){
                        int code = testProjectService.deleteByTestProjectId(testproject_id);
                        params.put("code", code);
                        params.put("success", true);
                        params.put("msg", "删除测试项目信息成功");
                    }
                }catch (IOException e2){
                    e2.printStackTrace();
                }
            }else{
                params.put("success", false);
                params.put("msg", "删除测试项目失败，测试项目不存在");
            }
        }catch(Exception e){
            e.printStackTrace();
            params.put("success", false);
            params.put("msg", "服务器异常");
        }
        return res;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public ModelAndView editTestProject(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/edit");
        try {
            int code = testProjectService.editTestProject(params);
            params.put("code", code);
            params.put("msg", "修改测试项目信息成功");
            params.put("success",true);
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
            List<Map<String, Object>> queryInfoList = testProjectService.queryPageList(params);
            if(queryInfoList.size() > 0) {
                System.out.println(queryInfoList);
                params.put("testProjectInfo", queryInfoList);
                params.put("msg", "获取测试项目信息成功");
                params.put("success", true);
            }else {
                params.put("success", false);
                params.put("msg", "没有找到相关测试项目信息");
            }
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return params;
    }

    @RequestMapping("/batchdelete")
    @ResponseBody
    public ModelAndView batchDelteTestProject(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/batchdelete");
        try {
            int projectCode = 1, moduleCode = 1,suiteCode = 1, pyscriptCode = 1;
            if(params.containsKey("testproject_ids")) {
                long[] testproject_ids = handler.handlerJsonArray(params.get("testproject_ids").toString()).stream().mapToLong(t -> t.longValue()).toArray();
                System.out.println(testproject_ids);
                projectCode = testProjectService.batchDeleteTestProject(testproject_ids);
            }
            if(params.containsKey("testmodule_ids")) {
                long[] testmodule_ids = handler.handlerJsonArray(params.get("testmodule_ids").toString()).stream().mapToLong(t -> t.longValue()).toArray();
                System.out.println(testmodule_ids);
                moduleCode = testModuleService.batchDeleteTestModule(testmodule_ids);
            }
            if(params.containsKey("suite_ids")) {
                long[] suite_ids = handler.handlerJsonArray(params.get("suite_ids").toString()).stream().mapToLong(t -> t.longValue()).toArray();
                System.out.println(suite_ids);
                suiteCode = testSuiteService.batchDeleteTestSuite(suite_ids);
            }
            if(params.containsKey("pyscript_ids")) {
                long[] pyscript_ids = handler.handlerJsonArray(params.get("pyscript_ids").toString()).stream().mapToLong(t -> t.longValue()).toArray();
                System.out.println(pyscript_ids);
                pyscriptCode = pyscriptService.batchDeletePyscript(pyscript_ids);
            }
            int code = projectCode + moduleCode + suiteCode + pyscriptCode;
            if(code==4){
                params.put("code", code);
                params.put("msg", "批量删除成功");
                params.put("success",true);
            }else {
                params.put("code", code);
                params.put("msg", "批量删除失败，code=4！");
                params.put("success",false);
            }
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return res;
    }

    @RequestMapping("/queryprojectcomponent")
    @ResponseBody
    public Map queryProjectComponent(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        try {
            System.out.println("querylist" + JSONUtils.toJSONString(params));
//            long testproject_id = Long.valueOf(params.get("testproject_id").toString());
            Map<String, Object> projectComponent = new HashMap<String, Object>();
            List<Map<String, Object>> moduleInfoList = testModuleService.queryPageList(params);
            List<Map<String, Object>> suiteInfoList = testSuiteService.queryPageList(params);
            List<Map<String, Object>> scriptInfoList = pyscriptService.queryPageList(params);
            projectComponent.put("moduleList", moduleInfoList);
            projectComponent.put("suiteList", suiteInfoList);
            projectComponent.put("scriptList", scriptInfoList);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            for(Map<String,Object> moduleInfo : moduleInfoList) {
                long testmodle_id = Long.valueOf(moduleInfo.get("testmodule_id").toString());
                paramMap.put("testmodule_id", testmodle_id);
                List<Map<String, Object>> moduleSubSuiteList = testSuiteService.queryPageList(paramMap);
                List<Map<String, Object>> moduleSubScriptList = pyscriptService.queryPageList(paramMap);
                if (moduleSubSuiteList.size() > 0) {
                    Map<String, Object> subparamMap = new HashMap<String, Object>();
                    for (Map<String, Object> moduleSubSuite : moduleSubSuiteList) {
                        long suite_id = Long.valueOf(moduleSubSuite.get("suite_id").toString());
                        subparamMap.put("suite_id", suite_id);
                        List<Map<String, Object>> suiteSubScriptList = pyscriptService.queryPageList(subparamMap);
                        subparamMap.clear();
                        moduleSubSuite.put("scriptList", suiteSubScriptList);
                    }
                }
                moduleInfo.put("suiteList", moduleSubSuiteList);
                moduleInfo.put("scriptList", moduleSubScriptList);
                paramMap.clear();
                System.out.println("#####" + moduleSubSuiteList + "\n" + moduleSubScriptList);
            }
//            List<Map<String, Object>> queryInfoList = testProjectService.queryPageList(params);
            if(projectComponent.size() > 0) {
//                System.out.println(moduleInfoList);
                params.put("projectComponent", projectComponent);
                params.put("msg", "获取信息成功");
                params.put("success", true);
            }else {
                params.put("success", false);
                params.put("msg", "没有找到相关信息");
            }
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return params;
    }
}
