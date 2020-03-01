package com.pkt.Service.TestProject;

import com.pkt.Dao.TestProject.TestSuiteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Component
@Service
public class TestSuiteService {
    @Autowired
    private TestSuiteDao testSuiteDao;

    public Map<String, Object> getBySuiteId(long suite_id){
        return testSuiteDao.getBySuiteId(suite_id);
    }

    public int addTestSuite(Map<String,Object> params){
        return testSuiteDao.addTestSuite(params);
    }

    public int deleteBySuiteId(long suite_id){
        return testSuiteDao.deleteBySuiteId(suite_id);
    }

    public int editTestSuite(Map<String,Object> params){
        return testSuiteDao.editTestSuite(params);
    }

    public List<Map<String, Object>> queryPageList(Map<String, Object> params){
        return testSuiteDao.queryPageList(params);
    }

    public List<Map<String, Object>> queryList(Map<String, Object> params){
        return testSuiteDao.queryList(params);
    }

    public List<Map<String, Object>> queryListByCollection (Map<String, Object> params){
        return testSuiteDao.queryListByCollection(params);
    }

    public int batchDeleteTestSuite(long[] suite_ids){
        return testSuiteDao.batchDeleteTestSuite(suite_ids);
    }
}
