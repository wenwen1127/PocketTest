package com.pkt.Service.Section;

import com.pkt.Dao.Section.SectionInfoDao;
import com.pkt.Entity.Section.Section;
import com.pkt.Entity.Section.SectionTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SectionInfoService {

    @Autowired
    private SectionInfoDao sectionInfoDao;

    public Map<String, Object> getBySectionId(int section_id){
        return sectionInfoDao.getBySectionId(section_id);
    }

    public int addSection(Map<String, Object> params){
        return sectionInfoDao.addSection(params);
    }

    public int deleteBySectionId(int section_id){
        return sectionInfoDao.deleteBySectionId(section_id);
    }

    public int editSection(Map<String, Object> params){
        return sectionInfoDao.editSection(params);
    }

    public List<SectionTree> querysubsection(int section_id){
        return sectionInfoDao.queryAllSections(section_id);
    }

    public SectionTree findFirstSection(){
        return sectionInfoDao.findFirstSection();
    }

//    public List<SectionTree> queryFullSectionById(int section_id){
//        return sectionInfoDao.queryFullSectionById(section_id);
//    }
}
