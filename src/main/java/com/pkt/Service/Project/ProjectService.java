package com.pkt.Service.Project;

import com.pkt.Dao.Project.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProjectService {
    @Autowired
    private ProjectDao projectDao;

    public Map<String, Object> getByProjectId(int project_id){
        return projectDao.getByProjectId(project_id);
    }

    public int addProject(Map<String, Object> params){
        return projectDao.addProject(params);
    }

    public int deleteByProjectId(int project_id){
        return projectDao.deleteByProjectId(project_id);
    }

    public int editProject(Map<String, Object> params){
        return projectDao.editProject(params);
    }

}
