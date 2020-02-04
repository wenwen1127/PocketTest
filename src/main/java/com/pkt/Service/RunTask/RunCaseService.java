package com.pkt.Service.RunTask;

import com.pkt.Dao.RunTask.RunCaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RunCaseService {
    @Autowired
    private RunCaseDao runCaseDao;

    public int addRunCaseInfo(Map<String, Object> params){
        return runCaseDao.addRunCaseInfo(params);
    }

    public Map<String, Object> getRunCaseInfoByParams(Map<String, Object> params){
        return runCaseDao.getRunCaseInfoByParams(params);
    }

}
