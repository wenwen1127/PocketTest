package com.pkt.Controller.User;

import com.alibaba.druid.support.json.JSONUtils;
import com.pkt.Handler.CommonHandler;
import com.pkt.Service.Section.SectionInfoService;
import com.pkt.Service.User.UserInfoService;
import com.pkt.Service.User.UserPwdService;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserPwdService userPwdService;
    @Autowired
    private SectionInfoService sectionInfoService;
    @Resource
    private CommonHandler handler;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap params)throws Exception {
        return "index";
    }

    @RequestMapping("/register")
    @ResponseBody
    public Map addUser(HttpServletRequest request) throws Exception{
        System.out.println("前端传入信息：" + request);
        Map<String,Object> params = handler.getParams(request);
        try {
            System.out.println("web有用户注册："+ JSONUtils.toJSONString(params) + "时间" + DateUtil.now());
            String mail = String.valueOf(params.get("user_mail"));
            String phone = String.valueOf(params.get("user_phone"));
            Map<String, Object> validData = new HashMap<String,Object>();
            validData.put("mail",mail);
            validData.put("phone",phone);
            int count = userInfoService.count(validData);
            if(count >= 1){
                System.out.println("当前用户已注册！");
                params.put("success",false);
                params.put("msg","当前用户已注册");
                return params;
            }
            int info_code = userInfoService.addUser(params);
            int pwd_code = userPwdService.add(params);
            System.out.println(DateUtil.now()+" 有用户注册，信息:  "+JSONUtils.toJSONString(params));
            params.put("code",info_code&pwd_code);
            params.put("success",true);
            params.put("msg","注册成功！");
            System.out.println(params);
        }catch (Exception e){
            e.printStackTrace();
            params.put("success", true);
            params.put("msg", "服务器异常");
        }
        return params;
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map checkUser(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        try {
            System.out.println("web有用户登录："+ JSONUtils.toJSONString(params) + "时间" + DateUtil.now());
            System.out.println("***** "+userInfoService.queryList(params)+" ******");
            if (userInfoService.queryList(params) != null && userInfoService.queryList(params).size()>0){
                params.putAll(userInfoService.queryList(params));
                System.out.println("params" + userPwdService.queryList(params));
                if(userPwdService.queryList(params) != null && userPwdService.queryList(params).size()>0){
//                    params.putAll(userInfoService.queryList(params));
                    System.out.println(params);
//                    params.put("user_password",String.valueOf(userPwdService.queryList(userInfoService.queryList(params))));
                    params.put("msg", "登录成功");
                    params.put("success", true);
                }
                else {
                    params.put("msg", "密码错误！");
                    params.put("success", false);
                }
            }
            else{
               params.put("msg", "用户不存在！");
               params.put("success",false);
            }
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg", "服务器错误");
            params.put("success",false);
        }
        return params;
    }
    @RequestMapping("/logout")
    @ResponseBody
    public ModelAndView logout(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/logout");
        params.put("user_id","");
        params.put("success",true);
        params.put("msg","退出登录成功");
        return res;
    }
    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getUserById(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        Map<String, Object> userInfo = userInfoService.getUserInfoById(Long.valueOf(params.get("user_id").toString()));
        if(userInfo.size()>0){
            params.putAll(userInfo);
            params.put("success",true);
            params.put("msg","获取成功！");
        }else {
            params.put("success",false);
        }
        return res;
    }

    @RequestMapping("/changepwd")
    @ResponseBody
    public ModelAndView changePassword(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/changepwd");
        Long user_id = Long.valueOf(params.get("user_id").toString());
//        System.out.println("******* " +userPwdService.getByUserId(user_id)+ " *******" );
        if(userPwdService.getByUserId(user_id) !=null &&userPwdService.getByUserId(user_id).size()>0){
            int code = userPwdService.ChangePwd(params);
            params.put("code", code);
            params.put("success",true);
            params.put("msg","修改成功！");
        }else {
            params.put("msg","原密码不正确，请重新输入！");
            params.put("success",false);
        }
        return res;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public ModelAndView editUser(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/edit");
        try {
            int code = userInfoService.edit(params);
            params.put("code", code);
            params.put("msg", "修改成功");
            params.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", "false");
        }
        return res;
    }

    @RequestMapping("/querypagelist")
    @ResponseBody
    public Map queryPageList(HttpServletRequest request){

        Map<String,Object> params = handler.getParams(request);
        System.out.println("querylist" + JSONUtils.toJSONString(params));
        try{
            List<Map<String, Object>> queryInfoList = userInfoService.queryPageList(params);
            if(queryInfoList.size() > 0){
                for (Map<String, Object> queryInfo : queryInfoList){
                    String section_name = sectionInfoService.getBySectionId(Integer.valueOf(queryInfo.get("section_id").toString())).get("section_name").toString();
                    queryInfo.put("section_name", section_name);
                }
                params.put("userInfoList", queryInfoList);
                System.out.println("****" + queryInfoList);
                params.put("success", true);
                params.put("msg", "成功获取用户信息");
            }else {
                params.put("success", false);
                params.put("msg", "没有找到相关用户");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return params;

    }




}
