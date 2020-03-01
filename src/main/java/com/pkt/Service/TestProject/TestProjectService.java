package com.pkt.Service.TestProject;

import com.pkt.Dao.TestProject.TestProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestProjectService {
    @Autowired
    private TestProjectDao testProjectDao;

    public Map<String, Object> getByTestProjectId(long testproject_id){
        return testProjectDao.getByTestProjectId(testproject_id);
    }

    public int addTestProject(Map<String,Object> params){
        return testProjectDao.addTestProject(params);
    }

    public int deleteByTestProjectId(long testproject_id){
        return testProjectDao.deleteByTestProjectId(testproject_id);
    }

    public int editTestProject(Map<String,Object> params){
        return testProjectDao.editTestProject(params);
    }

    public List<Map<String, Object>> queryPageList(Map<String, Object> params){
        return testProjectDao.queryPageList(params);
    }

    public List<Map<String, Object>> queryList(Map<String,Object> params){
        return testProjectDao.queryList(params);
    }

    public List<Map<String, Object>> queryListByCollection (Map<String, Object> params){
        return testProjectDao.queryListByCollection(params);
    }

    public int batchDeleteTestProject(long[] testproject_ids){
        return testProjectDao.batchDeleteTestProject(testproject_ids);
    }
}
