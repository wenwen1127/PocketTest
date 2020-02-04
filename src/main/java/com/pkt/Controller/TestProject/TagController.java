package com.pkt.Controller.TestProject;

import com.pkt.Handler.CommonHandler;
import com.pkt.Service.TestProject.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/test/testproject/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @Resource
    private CommonHandler handler;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getByTagId(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        Map<String, Object> tagInfo = tagService.getByTagId(Integer.valueOf(params.get("tag_id").toString()));
        if(tagInfo.size()>0){
            params.putAll(tagInfo);
            params.put("success",true);
            params.put("msg","获取标签信息成功！");
        }else {
            params.put("success",false);
            params.put("msg","获取标签信息失败");
        }
        return res;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ModelAndView addTag(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/add");
        System.out.println("增加标签信息" + params);
        try {
            int code = tagService.addTag(params);
            params.put("code", code);
            params.put("success", true);
            params.put("msg", "添加标签信息成功");

        }catch(Exception e){
            e.printStackTrace();
            params.put("success", false);
            params.put("msg", "服务器异常");
        }
        return res;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ModelAndView deleteByTagId(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/delete");
        try {
            int tag_id = Integer.valueOf(params.get("tag_id").toString());
            if (tagService.getByTagId(tag_id) != null && tagService.getByTagId(tag_id).size()>0){
                int code = tagService.deleteByTagId(tag_id);
                params.put("code", code);
                params.put("success", true);
                params.put("msg", "删除标签信息成功");
            }else{
                params.put("success", false);
                params.put("msg", "删除标签失败，标签不存在");
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
    public ModelAndView editTag(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/edit");
        try {
            int code = tagService.editTag(params);
            params.put("code", code);
            params.put("msg", "修改标签信息成功");
            params.put("success",true);

        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return res;
    }
}

