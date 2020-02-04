package com.pkt.Service.TestProject;

import com.pkt.Dao.TestProject.TestModuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestModuleService {
    @Autowired
    private TestModuleDao testModuleDao;

    public Map<String, Object> getByTestModuleId(long testmodule_id){
        return testModuleDao.getByTestModuleId(testmodule_id);
    }

    public int addTestModule(Map<String, Object> params){
        return testModuleDao.addTestModule(params);
    }

    public int deleteByTestModuleId(long testmodule_id){
        return testModuleDao.deleteByTestModuleId(testmodule_id);
    }

    public int editTestModule(Map<String, Object> params){
        return testModuleDao.editTestModule(params);
    }

    public Map<String, Object> queryPageList(Map<String, Object> params){
        return testModuleDao.queryPageList(params);
    }

    public int batchDeleteTestModule(long[] testmodule_ids){
        return testModuleDao.batchDeleteTestModule(testmodule_ids);
    }

}
