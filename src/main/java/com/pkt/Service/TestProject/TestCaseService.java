package com.pkt.Service.TestProject;

import com.pkt.Dao.TestProject.TestCaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestCaseService {
    @Autowired
    private TestCaseDao testCaseDao;

    public Map<String, Object> getByCaseId(long case_id){
        return testCaseDao.getByCaseId(case_id);
    }

    public int addTestCase(Map<String,Object> params){
        return testCaseDao.addTestCase(params);
    }

    public int deleteByCaseId(long case_id){
        return testCaseDao.deleteByCaseId(case_id);
    }

    public int editTestCase(Map<String,Object> params){
        return testCaseDao.editTestCase(params);
    }

    public List<Map<String, Object>> queryPageList(Map<String, Object> params){
        return testCaseDao.queryPageList(params);
    }

    public int deleteByParams(Map<String, Object> params){
        return testCaseDao.deleteByParams(params);
    }

    public int batchDeleteTestCase(long[] case_ids){
        return testCaseDao.batchDeleteTestCase(case_ids);
    }

    public Map<String, Object> getSuiteInfoByCaseId(long case_id){
        return testCaseDao.getSuiteInfoByCaseId(case_id);
    }

    public int updateCaseResult(Map<String,Object> params){
        return testCaseDao.updateCaseResult(params);
    }

    public long getCaseIdByParams(String case_name, long suite_id){
        return testCaseDao.getCaseIdByParams(case_name, suite_id);
    }
}
