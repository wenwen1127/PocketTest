package com.pkt.Service.Project;

import com.pkt.Dao.Project.ProjectVersionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<Map<String, Object>> queryPageList(Map<String,Object> params){
        return projectVersionDao.queryPageList(params);
    }
    public List<Map<String, Object>> queryList(Map<String,Object> params){
        return projectVersionDao.queryList(params);
    }
}
