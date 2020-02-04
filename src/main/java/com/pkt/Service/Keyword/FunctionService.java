package com.pkt.Service.Keyword;

import com.pkt.Dao.Keyword.FunctionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FunctionService {
    @Autowired
    private FunctionDao functionDao;

    public Map<String, Object> getByFunctionId(long function_id){
        return functionDao.getByFunctionId(function_id);
    }
    public Map<String, Object> getFunctionByName(String function_name){
        return functionDao.getFunctionByName(function_name);
    }
    public int addFunction(Map<String,Object> params){
        return functionDao.addFunction(params);
    }

    public int deleteByFunctionId(long function_id){
        return functionDao.deleteByFunctionId(function_id);
    }

    public int editFunction(Map<String,Object> params){
        return functionDao.editFunction(params);
    }

    public int deleteByParams(Map<String, Object> params){
        return functionDao.deleteByParams(params);
    }
}
