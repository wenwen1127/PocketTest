package com.pkt.Controller.Project;

import com.alibaba.druid.support.json.JSONUtils;
import com.pkt.Handler.CommonHandler;
import com.pkt.Service.Project.ProjectVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/project/version")
public class ProjectVersionController {
    @Autowired
    private ProjectVersionService projectVersionService;

    @Resource
    private CommonHandler handler;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getByVersionId(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        int version_id = Integer.valueOf(params.get("version_id").toString());
        Map<String, Object> projectVersionInfo = projectVersionService.getByVersionId(version_id);
        if (projectVersionInfo.size() > 0) {
            params.putAll(projectVersionInfo);
            params.put("success", true);
            params.put("msg", "获取版本信息成功！");
        } else {
            params.put("success", false);
            params.put("msg", "获取失败");
        }
        return res;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ModelAndView addProjectVersion(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/add");
        System.out.println("增加一个项目版本信息" + params);
        try {
            int code = projectVersionService.addVersion(params);
            params.put("code", code);
            params.put("success", true);
            params.put("msg", "添加版本信息成功");

        }catch(Exception e){
            e.printStackTrace();
            params.put("success", false);
            params.put("msg", "服务器异常");
        }
        return res;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ModelAndView deleteByVersionId(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/delete");
        try {
            int version_id = Integer.valueOf(params.get("version_id").toString());
            if (projectVersionService.getByVersionId(version_id) != null && projectVersionService.getByVersionId(version_id).size()>0){
                int code = projectVersionService.deleteByVersionId(version_id);
                params.put("code", code);
                params.put("success", true);
                params.put("msg", "删除版本信息成功");
            }else{
                params.put("success", false);
                params.put("msg", "删除失败，当前版本不存在");
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
    public ModelAndView editProjectVersion(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/edit");
        try {
            int code = projectVersionService.editVersion(params);
            params.put("code", code);
            params.put("msg", "修改成功");
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
    public Map queryPageList(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        System.out.println("querylist" + JSONUtils.toJSONString(params));
        try{
            List<Map<String, Object>> queryInfoList = projectVersionService.queryPageList(params);
            if(queryInfoList.size() > 0){
                for (Map<String, Object> queryInfo : queryInfoList){
                    String project_name = params.get("project_name").toString();
                    queryInfo.put("project_name", project_name);
                }
                params.put("versionInfoList", queryInfoList);
                System.out.println("****" + queryInfoList);
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

    @RequestMapping("/querylist")
    @ResponseBody
    public Map queryList(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);

        try{
//            for(String proName : (List<String>)params.get("project_name")){
//
//            }
            String[] projectName = params.get("project_name").toString().split(",");
            List<Map<String, Object>> queryInfoList = new ArrayList<>();
            Map<String, Object> nameMap = new HashMap<String, Object>();
            System.out.println("querylist" + projectName);
            for(String proName : projectName){
                nameMap.put("project_name", proName);
                queryInfoList.addAll(projectVersionService.queryList(nameMap));
                System.out.println("****" + queryInfoList);
                nameMap.clear();
            }
            if(queryInfoList.size() > 0){
                params.put("versionInfoList", queryInfoList);

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
