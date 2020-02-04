package com.pkt.Controller.Section;


import com.pkt.Entity.Section.SectionTree;
import com.pkt.Handler.CommonHandler;
import com.pkt.Service.Section.SectionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private SectionInfoService sectionInfoService;
    @Resource
    private CommonHandler handler;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getBySectionId(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        int section_id = Integer.valueOf(params.get("section_id").toString());
        Map<String, Object> sectionInfo = sectionInfoService.getBySectionId(section_id);
        if(sectionInfo.size()>0){
            params.putAll(sectionInfo);
            params.put("success",true);
            params.put("msg","获取部门信息成功！");
        }else {
            params.put("success",false);
            params.put("msg","获取部门信息失败");
        }
        return res;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ModelAndView addSection(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/add");
        System.out.println("增加部门信息" + params);
        try {
            int code = sectionInfoService.addSection(params);
            params.put("code", code);
            params.put("success", true);
            params.put("msg", "添加部门信息成功");
        }catch(Exception e){
            e.printStackTrace();
            params.put("success", false);
            params.put("msg", "服务器异常");
        }
        return res;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ModelAndView deleteBySectionId(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/delete");
        try {
            int section_id = Integer.valueOf(params.get("section_id").toString());
            if (sectionInfoService.getBySectionId(section_id) != null && sectionInfoService.getBySectionId(section_id).size()>0){
                int code = sectionInfoService.deleteBySectionId(section_id);
                params.put("code", code);
                params.put("success", true);
                params.put("msg", "删除部门信息成功");
            }else{
                params.put("success", false);
                params.put("msg", "删除失败，部门信息不存在");
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
    public ModelAndView editSection(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/edit");
        try {
            int code = sectionInfoService.editSection(params);
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

    @RequestMapping("/querysectionList")
    @ResponseBody
    public Map querySubSectionById(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        try {
//            int section_id = Integer.valueOf(params.get("section_id").toString());
            SectionTree root_section = sectionInfoService.findFirstSection();
            Map resultMap = handler.getSectionTree(root_section);
            List resultList = new ArrayList();
            resultList.add(resultMap);
            System.out.println( resultMap + "!!!!!Result");
            params.put("sectionList",resultList);
            params.put("success",true);
            params.put("msg", "所有部门信息获取成功");
        }catch(Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return params;
    }

//    @RequestMapping("/queryfullsection")
//    @ResponseBody
//    public ModelAndView queryFullSectionById(HttpServletRequest request) {
//        Map<String, Object> params = handler.getParams(request);
//        ModelAndView res = new ModelAndView();
//        res.addObject("params", params);
//        res.setViewName("/queryfullsection");
//        try {
//            int section_id = Integer.valueOf(params.get("section_id").toString());
//            List<SectionTree> result = sectionInfoService.queryFullSectionById(section_id);
//            System.out.println(handler.getSectionTree(result.get(0)) + "!!!!!Result");
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return res;
//    }



}
