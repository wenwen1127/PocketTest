package com.pkt.Service.TestProject;

import com.pkt.Dao.TestProject.TestPlanDao;
import com.pkt.Dao.TestProject.TestPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
@Service
public class TestPointService {
    @Autowired
    private TestPointDao testPointDao;

    public Map<String, Object> getByTestPointId(long testpoint_id){
        return testPointDao.getByTestPointId(testpoint_id);
    }

    public int addTestPlan(Map<String,Object> params){
        return testPointDao.addTestPoint(params);
    }

    public int editTestPlan(long testpoint_id){
        return testPointDao.editTestPoint(testpoint_id);
    }

}
