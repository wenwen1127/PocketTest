package com.pkt.Controller.Keyword;

import com.pkt.Common.constant.KeywordConstant;
import com.pkt.Handler.CommonHandler;
import com.pkt.Service.Keyword.BuildInKeywordService;
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
@RequestMapping("/test/buildinkeyword")
public class BuildInKeywordController {
    @Autowired
    private BuildInKeywordService buildInKeywordService;
    @Resource
    CommonHandler handler;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getBuildinKeywordByName(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        Map<String, Object> buildinKeywordInfo = buildInKeywordService.getBuildinKeywordByName(params.get("bldinkeyword_name").toString());
        if(buildinKeywordInfo.size()>0){
            params.putAll(buildinKeywordInfo);
            params.put("success",true);
            params.put("msg","获取内部关键字信息成功！");
        }else {
            params.put("success",false);
            params.put("msg","获取外内部关键字信息失败");
        }
        return res;
    }

    @RequestMapping("/querypagelist")
    @ResponseBody
    public Map queryPageList(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        try {
            String[] buildInKeyword = KeywordConstant.BUILD_IN_KEYWORD;
            String[] buildInDescribe = KeywordConstant.BUILD_IN_DESCRIBE;
            List<Map<String, String>> buildInKeywordInfo = new ArrayList<>();
            for(int index=0; index<buildInKeyword.length; index++){
                Map<String, String> tempMap = new HashMap<>();
                tempMap.put("name", buildInKeyword[index]);
                tempMap.put("describe", buildInDescribe[index]);
                buildInKeywordInfo.add(tempMap);
            }
            System.out.println(buildInKeywordInfo);

            params.put("buildInKeywordInfo", buildInKeywordInfo);
            params.put("msg", "获取内置关键字分页信息成功");
            params.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return params;
    }


}
