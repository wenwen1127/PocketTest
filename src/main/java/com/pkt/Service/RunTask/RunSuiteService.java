package com.pkt.Service.RunTask;

import com.pkt.Dao.RunTask.RunSuiteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RunSuiteService {
    @Autowired
    private RunSuiteDao runSuiteDao;
    public int addRunSuiteInfo(Map<String, Object> params){
        return runSuiteDao.addRunSuiteInfo(params);
    }
    public List<Map<String, Object>> getRunSuiteInfoByParams(Map<String, Object> params){
        return runSuiteDao.getRunSuiteInfoByParams(params);
    }

    public List<Map<String, Object>> completedTask(String startDate){
        return runSuiteDao.completedTask(startDate);
    }

}
