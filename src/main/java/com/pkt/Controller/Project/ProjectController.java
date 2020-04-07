package com.pkt.Controller.Project;

import com.alibaba.druid.support.json.JSONUtils;
import com.pkt.Entity.Section.Section;
import com.pkt.Handler.CommonHandler;
import com.pkt.Service.Project.ProjectService;
import com.pkt.Service.Section.SectionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private SectionInfoService sectionInfoService;
    @Resource
    private CommonHandler handler;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getByProjectId(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        Map<String, Object> projectInfo = projectService.getByProjectId(Integer.valueOf(params.get("project_id").toString()));
        if(projectInfo.size()>0){
            params.putAll(projectInfo);
            params.put("success",true);
            params.put("msg","获取成功！");
        }else {
            params.put("success",false);
            params.put("msg","获取失败");
        }
        return res;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map addProject(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        try {
            Map<String, Object> data = (Map<String, Object>)JSONUtils.parse(params.get("data").toString());
            System.out.println("增加项目信息" + data);
            int code = projectService.addProject(data);
            params.put("code", code);
            params.put("success", true);
            params.put("msg", "添加成功");

        }catch(Exception e){
            e.printStackTrace();
            params.put("success", false);
            params.put("msg", "服务器异常");
        }
        return params;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ModelAndView deleteByProjectId(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/delete");
        try {
            int project_id = Integer.valueOf(params.get("project_id").toString());
            if (projectService.getByProjectId(project_id) != null && projectService.getByProjectId(project_id).size()>0){
                int code = projectService.deleteByProjectId(project_id);
                params.put("code", code);
                params.put("success", true);
                params.put("msg", "删除成功");
            }else{
                params.put("success", false);
                params.put("msg", "删除失败，项目不存在");
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
    public ModelAndView editProject(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/edit");
        try {
            int code = projectService.editProject(params);
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
            List<Map<String, Object>> queryInfoList = projectService.queryPageList(params);
            if(queryInfoList.size() > 0){
                params.put("projectInfoList", queryInfoList);
//                System.out.println("****" + queryInfoList);
                params.put("success", true);
                params.put("msg", "成功获取项目信息");
            }else {
                params.put("success", false);
                params.put("msg", "没有找到相关项目信息");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return params;

    }
}
