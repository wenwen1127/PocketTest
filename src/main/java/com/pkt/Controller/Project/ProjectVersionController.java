package com.pkt.Controller.Project;

import com.pkt.Handler.CommonHandler;
import com.pkt.Service.Project.ProjectVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

}
