package com.pkt.Service.TestProject;

import com.pkt.Dao.TestProject.TestPlanDao;
import com.pkt.Dao.TestProject.TestSuiteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component
@Service
public class TestPlanService {
    @Autowired
    private TestPlanDao testPlanDao;

    public Map<String, Object> getByTestPlanId(long testplan_id){
        return testPlanDao.getByTestPlanId(testplan_id);
    }

    public int addTestPlan(Map<String,Object> params){
        return testPlanDao.addTestPlan(params);
    }

    public int editTestPlan(long testplan_id){
        return testPlanDao.editTestPlan(testplan_id);
    }


}
