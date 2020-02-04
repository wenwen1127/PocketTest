package com.pkt.Controller.Keyword;

import com.pkt.Common.constant.CommonConstant;
import com.pkt.Handler.CommonHandler;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.Keyword.ExternalKeywordService;
import com.pkt.Service.Keyword.FunctionService;
import com.pkt.Service.Keyword.PyscriptService;
import com.pkt.Service.TestProject.TestModuleService;
import com.pkt.Service.TestProject.TestProjectService;
import com.pkt.Service.TestProject.TestSuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/test/pyscript")
public class PyscriptController{

    @Autowired
    private PyscriptService pyscriptService;
    @Autowired
    private TestProjectService testProjectService;
    @Autowired
    private TestModuleService testModuleService;
    @Autowired
    private TestSuiteService testSuiteService;
    @Autowired
    private FunctionService functionService;
    @Autowired
    private ExternalKeywordService externalKeywordService;

    @Resource
    CommonHandler handler;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getByPyscriptId(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        Map<String, Object> pyscriptInfo = pyscriptService.getByPyscriptId(Integer.valueOf(params.get("pyscript_id").toString()));
        if(pyscriptInfo.size()>0){
            params.putAll(pyscriptInfo);
            params.put("success",true);
            params.put("msg","获取脚本信息成功！");
        }else {
            params.put("success",false);
            params.put("msg","获取脚本信息失败");
        }
        return res;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public ModelAndView deleteByPyscriptId(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/delete");
        try {
            long pyscript_id = Long.valueOf(params.get("pyscript_id").toString());
            if (pyscriptService.getByPyscriptId(pyscript_id) != null && pyscriptService.getByPyscriptId(pyscript_id).size()>0){
                String filepath = pyscriptService.getByPyscriptId(Long.valueOf(params.get("pyscript_id").toString())).get("file_path").toString();
                try {
                    if(FileHandler.deleteFile(filepath)){
                        int pyscript_code = pyscriptService.deleteByPyscriptId(pyscript_id);
                        params.put("pyscript_code", pyscript_code);
                        params.put("success", true);
                        params.put("msg", "删除脚本信息成功");
                    }
                }catch (IOException e2){
                    e2.printStackTrace();
                }
            }else{
                params.put("success", false);
                params.put("msg", "删除脚本信息失败，脚本不存在");
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
    public ModelAndView editTestSuite(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/edit");

        try {
            //  修改module_id和project_id时同时要修改file_path

            if(params.containsKey("content")) {
                long pyscript_id = Long.valueOf(params.get("pyscript_id").toString());
                String filepath = pyscriptService.getByPyscriptId(pyscript_id).get("file_path").toString();
                String modify_time = FileHandler.modifyFile(filepath, params.get("content").toString()).toString();
                params.put("modify_time", modify_time);

                //   case update due to the suite update

//                String afterContent = params.get("content").toString();
//                List<String> functionNameList = PyscriptConvert.separatePyscript(afterContent);
//                Map<String, Object> functionInfo = new HashMap<String, Object>();
//                for (String functionName: functionNameList){
//                    functionInfo.putAll(params);
//                    functionInfo.put("function_name", functionName);
//                    int function_code = functionService.addFunction(params);
//                    int extkeyword_code = externalKeywordService.addExternalKeyword(params);
//                    if(function_code == 0 || extkeyword_code==0){
//                        params.put("function_code", function_code);
//                        params.put("extkeyword_code", extkeyword_code);
//                        params.put("msg", "function/extkeyword信息添加失败");
//                        params.put("success",false);
//                        return res;
//                    }
//                    functionInfo.clear();
//                }
            }
            int code = pyscriptService.editPyscript(params);

            params.put("code", code);
            params.put("msg", "修改脚本信息成功");
            params.put("success",true);

        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return res;
    }

    @RequestMapping("/import")
    @ResponseBody
    public ModelAndView importPyscript(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/import");
        try {
            String src_path = params.get("filepath").toString();

            String[] strArray = src_path.trim().split(CommonConstant.FILE_SEPARATOR);
            String pyscriptname = strArray[strArray.length-1].split("\\.")[0];
            System.out.println(pyscriptname);
            String folderpath;
            if(params.containsKey("testproject_id")){
                folderpath = testProjectService.getByTestProjectId(Long.valueOf(params.get("testproject_id").toString())).get("testproject_path").toString();
            }else if(params.containsKey("testmodule_id")){
                folderpath = testModuleService.getByTestModuleId(Long.valueOf(params.get("testmodule_id").toString())).get("testmodule_path").toString();
            }else if(params.containsKey("suite_id")){
                String suitepath = testSuiteService.getBySuiteId(Long.valueOf(params.get("suite_id").toString())).get("file_path").toString();
                String suiteName = testSuiteService.getBySuiteId(Long.valueOf(params.get("suite_id").toString())).get("suite_name").toString();
                folderpath = FileHandler.createFolder(suitepath.substring(0,suitepath.lastIndexOf(CommonConstant.FILE_SEPARATOR)), suiteName).get("folder_path").toString();
            }else {
                params.put("msg", "testproject_id, testmodule_id, suite_id必须提供一个");
                params.put("success", false);
                return res;
            }
            String dst_path = folderpath + CommonConstant.FILE_SEPARATOR + strArray[strArray.length-1];
            try {
                Map<String, Date> fileInfo = FileHandler.CopyFile(src_path, dst_path);
                String create_time = fileInfo.get("create_time").toString();
                String modify_time = fileInfo.get("modify_time").toString();
                params.put("create_time", create_time);
                params.put("modify_time", modify_time);
            } catch (IOException e2){
                e2.printStackTrace();
            }
            params.put("pyscript_name", pyscriptname);
            params.put("file_path", dst_path);
            params.remove("filepath");

            int code = pyscriptService.addPyscript(params);
            if(code == 1){
                params.put("code", code);
                params.put("msg", "导入脚本成功");
                params.put("success",true);

            }else {
                params.put("code", code);
                params.put("msg", "导入脚本失败");
                params.put("success",false);
            }

        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return res;
    }

    @RequestMapping("/querypagelist")
    @ResponseBody
    public ModelAndView queryPageList(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/querypagelist");
        try {
            Map<String,Object> pageList = pyscriptService.queryPageList(params);
            params.put("pagelist", pageList);
            params.put("msg", "获取pyscript分页信息成功");
            params.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return res;
    }
    @RequestMapping("/getcontent")
    @ResponseBody
    public ModelAndView getPyscriptContent(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/getcontent");
        try {
            String filepath = pyscriptService.getByPyscriptId(Long.valueOf(params.get("pyscript_id").toString())).get("file_path").toString();
            try {
                String pyscriptContent = FileHandler.readFile(filepath);
                params.put("content", pyscriptContent);
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
        return res;
    }
}
