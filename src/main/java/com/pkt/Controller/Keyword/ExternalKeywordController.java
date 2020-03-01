package com.pkt.Controller.Keyword;

import checkers.oigj.quals.O;
import com.pkt.Common.convert.PyscriptConvert;
import com.pkt.Handler.CommonHandler;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.Keyword.ExternalKeywordService;
import com.pkt.Service.Keyword.GlobalScriptService;
import com.pkt.Service.Section.SectionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test/externalkeyword")
public class ExternalKeywordController {
    @Autowired
    private ExternalKeywordService externalKeywordService;
    @Autowired
    private GlobalScriptService globalScriptService;
    @Autowired
    private SectionInfoService sectionInfoService;

    @Resource
    CommonHandler handler;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getExternalKeywordByName(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        Map<String, Object> externalKeywordInfo = externalKeywordService.getExternalKeywordByName(params.get("exkeyword_name").toString());
        if(externalKeywordInfo.size()>0){
            params.putAll(externalKeywordInfo);
            params.put("success",true);
            params.put("msg","获取外部关键字信息成功！");
        }else {
            params.put("success",false);
            params.put("msg","获取外部关键字信息失败");
        }
        return res;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ModelAndView addExternalKeyword(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/add");
        System.out.println("增加外部关键字信息" + params);
        try {
            int code = externalKeywordService.addExternalKeyword(params);
            params.put("code", code);
            params.put("success", true);
            params.put("msg", "添加外部关键字信息成功");

        }catch(Exception e){
            e.printStackTrace();
            params.put("success", false);
            params.put("msg", "服务器异常");
        }
        return res;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ModelAndView deleteByExKeywordId(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/delete");
        try {
            int exkeyword_id = Integer.valueOf(params.get("exkeyword_id").toString());
            if (externalKeywordService.getByExKeywordId(exkeyword_id) != null && externalKeywordService.getByExKeywordId(exkeyword_id).size()>0){
                int code = externalKeywordService.deleteByExKeywordId(exkeyword_id);
                params.put("code", code);
                params.put("success", true);
                params.put("msg", "删除外部关键字信息成功");
            }else{
                params.put("success", false);
                params.put("msg", "删除外部关键字信息失败，函数不存在");
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
    public ModelAndView editExternalKeyword(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/edit");
        try {
            int code = externalKeywordService.editExternalKeyword(params);
            params.put("code", code);
            params.put("msg", "修改外部关键字信息成功");
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
            List<Map<String, Object>> scriptList = globalScriptService.queryPageList(params);
            List<Map<String, Object>> keywordList = new ArrayList<>();
            String section_name = sectionInfoService.getBySectionId(Integer.valueOf(params.get("section_id").toString())).get("section_name").toString();
            System.out.println(scriptList);
            try {
                for(Map<String, Object> scriptmap : scriptList){
                    String filepath = scriptmap.get("file_path").toString();
                    String scriptContent = FileHandler.readFile(filepath);
                    List<Map<String, Object>> tempList = PyscriptConvert.getGlobalKeyword(scriptContent,scriptmap.get("globalscript_name").toString(), section_name);
                    System.out.println(tempList);
                    keywordList.addAll(tempList);
                }
            }catch (IOException e1){
                e1.printStackTrace();
            }
            params.put("keywordList", keywordList);
            params.put("success",true);

        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return params;
    }




}
