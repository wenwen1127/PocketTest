package com.pkt.Entity.Section;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionTree {
    private int sectionId;
    private String sectionName;
    private List<SectionTree> sectionTreeList;

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<SectionTree> getSectionTreeList() {
        return sectionTreeList;
    }

    public void setSectionTreeList(List<SectionTree> sectionTreeList) {
        this.sectionTreeList = sectionTreeList;
    }

    public SectionTree(int sectionId, String sectionName, List<SectionTree> sectionTreeList) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.sectionTreeList = sectionTreeList;
    }

    public SectionTree() {
    }

    public Map<String, Object> getMap(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("section_id",sectionId);
        map.put("section_name",sectionName);
        map.put("sectionTreeList",sectionTreeList);
        return  map;
    }

}
