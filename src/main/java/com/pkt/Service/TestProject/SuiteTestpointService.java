package com.pkt.Service.TestProject;

import com.pkt.Dao.TestProject.SuiteTestpointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component
@Service
public class SuiteTestpointService {

    @Autowired
    private SuiteTestpointDao suiteTestpointDao;

    public List<Long> getBySuiteId(long suite_id){
        return suiteTestpointDao.getBySuiteId(suite_id);
    }

    public int add(Map<String,Object> params){
        return suiteTestpointDao.add(params);
    }

    public int count(long suite_id){
        return suiteTestpointDao.count(suite_id);
    }

}
