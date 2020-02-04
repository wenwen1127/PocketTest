package com.pkt.Controller.Keyword;

import com.pkt.Handler.CommonHandler;
import com.pkt.Service.Keyword.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/test/function")
public class FunctionController {
    @Autowired
    private FunctionService functionService;
    @Resource
    CommonHandler handler;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getFunctionByName(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        Map<String, Object> functionInfo = functionService.getFunctionByName(params.get("function_name").toString());
        if(functionInfo.size()>0){
            params.putAll(functionInfo);
            params.put("success",true);
            params.put("msg","获取函数信息成功！");
        }else {
            params.put("success",false);
            params.put("msg","获取函数信息失败");
        }
        return res;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ModelAndView addFunction(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/add");
        System.out.println("增加函数信息" + params);
        try {
            int code = functionService.addFunction(params);
            params.put("code", code);
            params.put("success", true);
            params.put("msg", "添加函数信息成功");

        }catch(Exception e){
            e.printStackTrace();
            params.put("success", false);
            params.put("msg", "服务器异常");
        }
        return res;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ModelAndView deleteByFunctionId(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/delete");
        try {
            int function_id = Integer.valueOf(params.get("function_id").toString());
            if (functionService.getByFunctionId(function_id) != null && functionService.getByFunctionId(function_id).size()>0){
                int code = functionService.deleteByFunctionId(function_id);
                params.put("code", code);
                params.put("success", true);
                params.put("msg", "删除函数信息成功");
            }else{
                params.put("success", false);
                params.put("msg", "删除函数信息失败，函数不存在");
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
    public ModelAndView editFunction(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/edit");
        try {
            int code = functionService.editFunction(params);
            params.put("code", code);
            params.put("msg", "修改函数信息成功");
            params.put("success",true);

        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return res;
    }
}
