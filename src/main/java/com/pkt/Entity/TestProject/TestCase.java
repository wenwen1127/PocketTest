package com.pkt.Entity.TestProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestCase {
    private int caseId;
    private String caseName;
    private int suiteId;
    private String caseContent;
    private int pass_count;
    private int fail_count;

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public int getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(int suiteId) {
        this.suiteId = suiteId;
    }

    public String getCaseContent() {
        return caseContent;
    }

    public void setCaseContent(String caseContent) {
        this.caseContent = caseContent;
    }

    public int getPass_count() {
        return pass_count;
    }

    public void setPass_count(int pass_count) {
        this.pass_count = pass_count;
    }

    public int getFail_count() {
        return fail_count;
    }

    public void setFail_count(int fail_count) {
        this.fail_count = fail_count;
    }

    public TestCase(String caseName,String caseContent) {
        this.caseName = caseName;
        this.caseContent = caseContent;
    }

    public void tostring(){
        System.out.println("caseName: "+caseName + "," + "caseContent:\r\n" + caseContent);
    }

    public List<String> toStringList(){
        List<String> caselist = new ArrayList<String>();
        caselist.add(caseName);
        caselist.add(caseContent);
        return caselist;
    }

}
