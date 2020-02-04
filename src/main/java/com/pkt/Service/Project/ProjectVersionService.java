package com.pkt.Service.Project;

import com.pkt.Dao.Project.ProjectVersionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProjectVersionService {

    @Autowired
    private ProjectVersionDao projectVersionDao;

    public Map<String, Object> getByVersionId(int version_id){
        return projectVersionDao.getByVersionId(version_id);
    }
    public int addVersion(Map<String, Object> params){
        return projectVersionDao.addVersion(params);
    }
    public int deleteByVersionId(int version_id){
        return projectVersionDao.deleteByVersionId(version_id);
    }
    public int editVersion(Map<String, Object> params){
        return projectVersionDao.editVersion(params);
    }

}
