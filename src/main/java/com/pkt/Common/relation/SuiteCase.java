package com.pkt.Common.relation;

import com.pkt.Common.constant.SuiteConstant;
import com.pkt.Common.convert.SuiteConvert;
import com.pkt.Handler.FileHandler;
import com.pkt.Service.TestProject.TestCaseService;
import com.pkt.Service.TestProject.TestSuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class SuiteCase {
    @Autowired
    private TestSuiteService testSuiteService;
    @Autowired
    private TestCaseService testCaseService;

    /**
     * suite更新导致case更新
     * @param caseListBefore
     * @param caseListAfter
     * @param suite_id
     * @return
     */
    public int CaseUpdateDuetoSuiteUpdate(List<List<String>> caseListBefore, List<List<String>> caseListAfter, long suite_id){
        List<List<String>> temList = new ArrayList<List<String>>();
        temList.addAll(caseListBefore);
        caseListBefore.removeAll(caseListAfter);
        Map<String, Object> caseParams= new HashMap<String, Object>();
        int code = 1;
        for(Iterator<List<String>> iterBefore = caseListAfter.iterator(); iterBefore.hasNext();){
            List<String> caseBefore = iterBefore.next();
            System.out.println("###### Change Before: " + caseBefore.get(0) + " #####");
            caseParams.put("case_name", caseBefore.get(0));
            caseParams.put("suite_id", suite_id);
            int casecode = testCaseService.deleteByParams(caseParams);
//            while(casecode == 0){
//                casecode = testCaseService.deleteByParams(caseParams);
//            }
            caseParams.clear();
        }
        caseListAfter.removeAll(temList);

        for (Iterator<List<String>> iterAfter = caseListAfter.iterator(); iterAfter.hasNext();) {
            List<String> caseAfter = iterAfter.next();
            System.out.println("###### Change After: " + caseAfter.get(0) + " #####");
            caseParams.put("case_name", caseAfter.get(0));
            caseParams.put("suite_id", suite_id);
            int casecode = testCaseService.addTestCase(caseParams);
//            while(casecode == 0){
//                casecode = testCaseService.addTestCase(caseParams);
//            }
            caseParams.clear();
        }
        return code;
    }

    public Date SuiteFileUpdateDuetoCaseDelete(List<String> caseNames, String suite_path){
        Date modify_time = null;
        try {
            String suiteContent = FileHandler.readFile(suite_path);
            List<List<String>> caseList = SuiteConvert.separateCase(suiteContent);
//            System.out.println("caseNames: " + caseNames);
//            System.out.println("caseList \n\r" + caseList);
            for(String caseName : caseNames){
                for(List<String> caselst : caseList){
                    if(caseName.equals(caselst.get(0))){
                        suiteContent = suiteContent.replace(caselst.get(1),"");
                        suiteContent = suiteContent.replace(caselst.get(0),"");

                    }
                }
            }
            System.out.println("suitecontent: \n\r" + suiteContent);
//                        System.out.println("caselst.get(1)"+caselst.get(1));
            modify_time = FileHandler.modifyFile(suite_path, suiteContent);


        }catch (IOException e){
            e.printStackTrace();
        }

        return modify_time;
    }


}
