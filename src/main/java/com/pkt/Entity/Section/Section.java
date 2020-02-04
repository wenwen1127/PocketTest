package com.pkt.Entity.Section;

import java.util.List;

public class Section {
    private List<Section> secList;
    private int sectionId;
    private String sectionName;
    private int level;
    private int parentId;

    public List<Section> getSecList() {
        return secList;
    }

    public void setSecList(List<Section> secList) {
        this.secList = secList;
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Section(int sectionId, String sectionName, int level, int parentId) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.level = level;
        this.parentId = parentId;
//        System.out.println(sectionId+sectionName+level+parentId);
    }

    public Section(int sectionId) {
        this.sectionId = sectionId;
//        System.out.println(sectionId);
    }
}
