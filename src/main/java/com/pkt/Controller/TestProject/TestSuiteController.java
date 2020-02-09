package com.pkt.Controller.TestProject;

import com.pkt.Common.convert.SuiteConvert;
import com.pkt.Common.relation.SuiteCase;
import com.pkt.Handler.CommonHandler;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.TestProject.TestCaseService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test/testproject/suite")
public class TestSuiteController {
    @Autowired
    private TestSuiteService testSuiteService;
    @Autowired
    private TestModuleService testModuleService;
    @Autowired
    private TestProjectService testProjectService;
    @Autowired
    private TestCaseService testCaseService;
    @Resource
    private CommonHandler handler;
    @Resource
    private SuiteCase suiteCase;

    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView getBySuiteId(HttpServletRequest request){
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/get");
        Map<String, Object> suiteInfo = testSuiteService.getBySuiteId(Integer.valueOf(params.get("suite_id").toString()));
        if(suiteInfo.size()>0){
            params.putAll(suiteInfo);
            params.put("success",true);
            params.put("msg","获取测试套件信息成功！");
        }else {
            params.put("success",false);
            params.put("msg","获取测试套件信息失败");
        }
        return res;
    }

    // 添加suite的时候必须要提供testproject_id的信息  无论是test project下的还是testmodule下的
    @RequestMapping("/add")
    @ResponseBody
    public ModelAndView addTestSuite(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/add");
        System.out.println("增加测试套件信息" + params);
        try {
            String filename = params.get("suite_name").toString()+".txt";
            Map<String, Object> fileInfo = new HashMap<String, Object>();
            if(params.containsKey("testmodule_id")){
                long testmodule_id = Long.valueOf(params.get("testmodule_id").toString());
                String folderpath = testModuleService.getByTestModuleId(testmodule_id).get("testmodule_path").toString();
                try {
                    fileInfo = FileHandler.createFile(folderpath,filename);
                } catch (IOException e2){
                    e2.printStackTrace();
                }
            }
            else if(params.containsKey("testproject_id")){
                long testproject_id = Long.valueOf(params.get("testproject_id").toString());
                String folderpath = testProjectService.getByTestProjectId(testproject_id).get("testproject_path").toString();
                try {
                    fileInfo = FileHandler.createFile(folderpath,filename);
                } catch (IOException e2){
                    e2.printStackTrace();
                }
            }else {
                System.out.println("传入信息有误！testproject_id和testmodule_id至少存在一个");
            }
            params.put("create_time", fileInfo.get("create_time"));
            params.put("modify_time", fileInfo.get("modify_time"));
            params.put("file_path",fileInfo.get("file_path"));

            System.out.println(params);
            int code = testSuiteService.addTestSuite(params);
            params.put("code", code);
            params.put("success", true);
            params.put("msg", "添加测试套件信息成功");

        }catch(Exception e){
            e.printStackTrace();
            params.put("success", false);
            params.put("msg", "服务器异常");
        }
        return res;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ModelAndView deleteBySuiteId(HttpServletRequest request) throws Exception{
        Map<String,Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/delete");
        try {
            int suite_id = Integer.valueOf(params.get("suite_id").toString());
            if (testSuiteService.getBySuiteId(suite_id) != null && testSuiteService.getBySuiteId(suite_id).size()>0){
                String filepath = testSuiteService.getBySuiteId(Long.valueOf(params.get("suite_id").toString())).get("file_path").toString();
                try {
                    if(FileHandler.deleteFile(filepath)){
                        int suitecode = testSuiteService.deleteBySuiteId(suite_id);
                        int casecode = testCaseService.deleteByParams(params);
                        params.put("suitecode", suitecode);
                        params.put("casecode", casecode);
                        params.put("success", true);
                        params.put("msg", "删除测试套件信息成功");
                    }
                }catch (IOException e2){
                    e2.printStackTrace();
                }
            }else{
                params.put("success", false);
                params.put("msg", "删除测试套件失败，测试套件不存在");
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
            int case_code = 1;
            if(params.containsKey("content")){
                long suite_id  = Long.valueOf(params.get("suite_id").toString());
                String filepath = testSuiteService.getBySuiteId(suite_id).get("file_path").toString() ;
                //   case update due to the suite update

                try {

//                    String caseContentBefore = SuiteConvert.separateSuite(suiteContent).get(SuiteConstant.CASE_TITLE);
//                    String caseContentAfter  = SuiteConvert.separateSuite(params.get("content").toString()).get(SuiteConstant.CASE_TITLE);
                    String beforeContent = FileHandler.readFile(filepath);
                    List<List<String>> caseListBefore = SuiteConvert.separateCase(beforeContent);
                    params.put("modify_time", FileHandler.modifyFile(filepath,params.get("content").toString()).toString());
                    String afterContent = FileHandler.readFile(filepath);
                    List<List<String>> caseListAfter = SuiteConvert.separateCase(afterContent);
                    if(caseListBefore != caseListAfter){
                        case_code = suiteCase.CaseUpdateDuetoSuiteUpdate(caseListBefore, caseListAfter, suite_id);

                    }

                }catch (IOException e2){
                    e2.printStackTrace();
                }


                //   add ....
            }
            //  修改module_id和project_id时同时要修改file_path
            int code = testSuiteService.editTestSuite(params);
            if(code==1 && case_code==1) {
                params.put("code", code);
                params.put("case_code",code);
                params.put("msg", "修改测试套件信息成功");
                params.put("success", true);
            }else {
                params.put("code", code);
                params.put("case_code",case_code);
                params.put("msg", "修改测试套件信息失败");
                params.put("success", false);
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
            List<Map<String,Object>> pageList = testSuiteService.queryPageList(params);
            params.put("pagelist", pageList);
            params.put("msg", "获取suite分页信息成功");
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
    public ModelAndView getSuiteContent(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/getcontent");
        try {
            String filepath = testSuiteService.getBySuiteId(Long.valueOf(params.get("suite_id").toString())).get("file_path").toString();
            try {
                String suiteContent = FileHandler.readFile(filepath);
                params.put("content", suiteContent);
            }catch (IOException e2){
                e2.printStackTrace();
            }
            params.put("msg", "获取suite内容成功");
            params.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            params.put("msg","服务器异常");
            params.put("success", false);
        }
        return res;
    }

    @RequestMapping("/batchdelete")
    @ResponseBody
    public ModelAndView batchDelteTestProject(HttpServletRequest request) {
        Map<String, Object> params = handler.getParams(request);
        ModelAndView res = new ModelAndView();
        res.addObject("params", params);
        res.setViewName("/batchdelete");
        try {
            int suiteCode = 1, caseCode = 1;
            if(params.containsKey("suite_ids")) {
                long[] suite_ids = handler.handlerJsonArray(params.get("suite_ids").toString()).stream().mapToLong(t -> t.longValue()).toArray();
                suiteCode = testSuiteService.batchDeleteTestSuite(suite_ids);
                int tmpcode = 0;
                for(long suite_id : suite_ids){
                    tmpcode += testCaseService.deleteByParams(params);
                }
                if (tmpcode != suite_ids.length){
                    System.out.println("Fail delete the case of suite!!!");
                    suiteCode = 0;
                }
            }
            if(params.containsKey("case_ids")) {
                long[] case_ids = handler.handlerJsonArray(params.get("case_ids").toString()).stream().mapToLong(t -> t.longValue()).toArray();
                caseCode = testCaseService.batchDeleteTestCase(case_ids);
            }
            int code = suiteCode + caseCode;
            if(code==2){
                params.put("code", code);
                params.put("msg", "批量删除成功");
                params.put("success",true);
            }else {
                params.put("code", code);
                params.put("msg", "批量删除失败，code=2！");
                params.put("success",false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }


}
