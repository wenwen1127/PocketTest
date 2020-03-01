package com.pkt.Service.Keyword;

import com.pkt.Dao.Keyword.GlobalScriptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GlobalScriptService {
    @Autowired
    private GlobalScriptDao globalScriptDao;

    public Map<String, Object> getByGlobalScriptId(long globalscript_id){
        return globalScriptDao.getByGlobalScriptId(globalscript_id);
    }

    public Map<String, Object> getByGlobalScriptName(String globalscriopt_name){
        return globalScriptDao.getByGlobalScriptName(globalscriopt_name);
    }

    public int addGlobalScript(Map<String,Object> params){
        return globalScriptDao.addGlobalScript(params);
    }

    public int deleteByGlobalScriptId(long globalscript_id){
        return globalScriptDao.deleteByGlobalScriptId(globalscript_id);
    }

    public int editGlobalScript(Map<String, Object> params){
        return globalScriptDao.editGlobalScript(params);
    }

    public List<Map<String, Object>> queryPageList(Map<String, Object> params){
        return globalScriptDao.queryPageList(params);
    }

}
