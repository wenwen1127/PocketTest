package com.pkt.Service.TestProject;

import com.pkt.Dao.TestProject.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TagService {
    @Autowired
    private TagDao tagDao;

    public Map<String, Object> getByTagId(long tag_id){
        return tagDao.getByTagId(tag_id);
    }

    public int addTag(Map<String,Object> params){
        return tagDao.addTag(params);
    }

    public int deleteByTagId(long case_id){
        return tagDao.deleteByTagId(case_id);
    }

    public int editTag(Map<String,Object> params){
        return tagDao.editTag(params);
    }

}
