package com.pkt.Controller.Keyword;

import com.pkt.Common.constant.CommonConstant;
import com.pkt.Handler.CommonHandler;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.Keyword.GlobalScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/test/globalscript")
public class GlobalScriptController {

    @Autowired
    private GlobalScriptService globalScriptService;
    @Resource
    CommonHandler handler;

    @RequestMapping("/getContent")
    @ResponseBody
    public Map getContent(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        try {
            Map<String, Object> scriptInfo = globalScriptService.getByGlobalScriptName(params.get("globalscript_name").toString());
            String filepath = scriptInfo.get("file_path").toString();
            try {
                String scriptContent = FileHandler.readFile(filepath);
                params.put("content", scriptContent);
                params.put("msg", "获取脚本内容成功");
                params.put("success",true);
            }catch (IOException e2){
                e2.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return params;
    }

    @RequestMapping("/updateContent")
    @ResponseBody
    public Map updataContent(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        try {
            Map<String, Object> scriptInfo = globalScriptService.getByGlobalScriptName(params.get("globalscript_name").toString());
            long globalscript_id = Long.valueOf(scriptInfo.get("globalscript_id").toString());
            String filepath = scriptInfo.get("file_path").toString();
            try {
                String modify_time = FileHandler.modifyFile(filepath, params.get("content").toString()).toString();
                params.put("modify_time", modify_time);
                params.put("globalscript_id", globalscript_id);
                int code = globalScriptService.editGlobalScript(params);
                if(code==1){
                    params.put("msg", "获取脚本内容成功");
                    params.put("success",true);
                }else {
                    params.put("msg", "获取脚本内容失败");
                    params.put("success",true);
                }

            }catch (IOException e2){
                e2.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return params;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map addGlobalScript(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        try {
            String file_name = params.get("globalscript_name").toString();
            System.out.println(globalScriptService.getByGlobalScriptName(file_name));
            if(globalScriptService.getByGlobalScriptName(file_name)!=null&&globalScriptService.getByGlobalScriptName(file_name).size()>0){
                params.put("success", false);
                params.put("msg", "创建失败，文件名已存在！");
            }else {
                try {
                    Map<String, Object> fileInfo = FileHandler.createFile(CommonConstant.Global_Script_Folder, file_name);
                    String content = params.get("content").toString();
                    String modify_time = FileHandler.modifyFile(fileInfo.get("file_path").toString(), content).toString();
                    params.put("create_time", fileInfo.get("create_time"));
                    params.put("modify_time", modify_time);
                    params.put("file_path", fileInfo.get("file_path"));
                    int code = globalScriptService.addGlobalScript(params);
                    if(code==1){
                        params.put("msg", "添加成功");
                        params.put("success",true);
                    }else {
                        params.put("msg", "添加失败");
                        params.put("success",false);
                    }
                }catch (IOException e2){
                    e2.printStackTrace();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return params;
    }


}
