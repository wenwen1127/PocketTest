package com.pkt.Service.Keyword;

import com.pkt.Dao.Keyword.PyscriptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PyscriptService {
    @Autowired
    private PyscriptDao pyscriptDao;

    public Map<String, Object> getByPyscriptId(long pyscript_id){
        return pyscriptDao.getByPyscriptId(pyscript_id);
    }

    public int addPyscript(Map<String,Object> params){
        return pyscriptDao.addPyscript(params);
    }

    public int deleteByPyscriptId(long pyscript_id){
        return pyscriptDao.deleteByPyscriptId(pyscript_id);
    }

    public int editPyscript(Map<String,Object> params){
        return pyscriptDao.editPyscript(params);
    }

    public Map<String, Object> queryPageList(Map<String, Object> params){
        return pyscriptDao.queryPageList(params);
    }

    public int batchDeletePyscript(long[] pyscript_ids){
        return pyscriptDao.batchDeletePyscript(pyscript_ids);
    }

    public List<Map<String, Object>> getPyscriptByParams(Map<String, Object> params){
        return pyscriptDao.getPyscriptByParams(params);
    }




}
