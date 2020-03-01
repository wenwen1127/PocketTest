package com.pkt.Controller.TestProject;

import com.pkt.Handler.CommonHandler;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.TestProject.TestModuleService;
import com.pkt.Service.TestProject.TestProjectService;
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
@RequestMapping("/test/testproject/module")
public class TestModuleController {
    @Autowired
    private TestModuleService testModuleService;
    @Autowired
    private TestProjectService testProjectService;

    @Resource
    private CommonHandler handler;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getByModuleId(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        Map<String, Object> moduleInfo = testModuleService.getByTestModuleId(Integer.valueOf(params.get("testmodule_id").toString()));
        if(moduleInfo.size()>0){
            params.putAll(moduleInfo);
            params.put("success",true);
            params.put("msg","获取测试模块信息成功！");
        }else {
            params.put("success",false);
            params.put("msg","获取测试模块信息失败");
        }
        return res;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ModelAndView addModule(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/add");
        System.out.println("增加测试模块信息" + params);
        try {
            Map<String, Object> folderInfo = new HashMap<String, Object>();
            int testproject_id = Integer.valueOf(params.get("testproject_id").toString());
            Map<String, Object> testProjectInfo = testProjectService.getByTestProjectId(testproject_id);
            System.out.println(testProjectInfo);
            String testproject_folder = testProjectInfo.get("testproject_path").toString();
            String testproject_name =  params.get("testmodule_name").toString();
            String testmodule_path = testproject_folder+ "/" + testproject_name;
            try {
                folderInfo = FileHandler.createFolder(testproject_folder, testproject_name);
                params.put("create_time", folderInfo.get("create_time"));
                params.put("modify_time", folderInfo.get("modify_time"));
                params.put("testmodule_path", testmodule_path);
            }catch(IOException e2){
                e2.printStackTrace();
            }
            int code = testModuleService.addTestModule(params);
            params.put("code", code);
            params.put("success", true);
            params.put("msg", "添加测试模块信息成功");
        }catch(Exception e){
            e.printStackTrace();
            params.put("success", false);
            params.put("msg", "服务器异常");
        }
        return res;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ModelAndView deleteByModuleId(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/delete");
        try {
            int testmodule_id = Integer.valueOf(params.get("testmodule_id").toString());
            Map<String,Object> testModuleInfo = testModuleService.getByTestModuleId(testmodule_id);
            if (testModuleInfo != null && testModuleInfo.size()>0){
                String testmodule_path = testModuleInfo.get("testmodule_path").toString();
                try {
                    if(FileHandler.deleteFolder(testmodule_path)){
                        int code = testModuleService.deleteByTestModuleId(testmodule_id);
                        params.put("code", code);
                        params.put("success", true);
                        params.put("msg", "删除测试模块信息成功");
                    }
                }catch (IOException e2){
                    e2.printStackTrace();
                }
            }else{
                params.put("success", false);
                params.put("msg", "删除测试模块失败，测试模块不存在");
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
    public ModelAndView editModule(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/edit");
        try {
            if(params.containsKey("testproject_id")){
                int testproject_id = Integer.valueOf(params.get("testproject_id").toString());
                Map<String, Object> testProjectInfo = testProjectService.getByTestProjectId(testproject_id);
                String testmodule_name = testModuleService.getByTestModuleId(Integer.valueOf(params.get("testmodule_id").toString())).get("testmodule_name").toString();
                String testmodule_path = testProjectInfo.get("testproject_path").toString() + "/" + testmodule_name;
                params.put("testmodule_path", testmodule_path);
            }
            int code = testModuleService.editTestModule(params);
            params.put("code", code);
            params.put("msg", "修改测试模块信息成功");
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
    public ModelAndView queryPageList(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/querypagelist");
        try {
            List<Map<String,Object>> pageList = testModuleService.queryPageList(params);
            params.put("pagelist", pageList);
            params.put("msg", "Module获取分页信息成功");
            params.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return res;
    }

    @RequestMapping("/querylist")
    @ResponseBody
    public Map queryList(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        try{
//
            String[] testprojectName = params.get("testproject_name").toString().split(",");
            List<Map<String, Object>> queryInfoList = new ArrayList<>();
            Map<String, Object> nameMap = new HashMap<String, Object>();
            System.out.println("querylist" + testprojectName);
            for(String testproName : testprojectName){
                nameMap.put("testproject_name", testproName);
                queryInfoList.addAll(testModuleService.queryList(nameMap));
                System.out.println("****" + queryInfoList);
                nameMap.clear();
            }
            if(queryInfoList.size() > 0){
                params.put("moduleInfoList", queryInfoList);

                params.put("success", true);
                params.put("msg", "成功获取版本信息");
            }else {
                params.put("success", false);
                params.put("msg", "没有找到相关版本信息");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return params;
    }
}
