package com.pkt.Service.Keyword;

import com.pkt.Dao.Keyword.ExternalKeywordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExternalKeywordService {
    @Autowired
    private ExternalKeywordDao externalKeywordDao;

    public Map<String, Object> getByExKeywordId(long exkeyword_id){
        return externalKeywordDao.getByExKeywordId(exkeyword_id);
    }
    public Map<String, Object> getExternalKeywordByName(String function_name){
        return externalKeywordDao.getExternalKeywordByName(function_name);
    }
    public int addExternalKeyword(Map<String,Object> params){
        return externalKeywordDao.addExtenalKeyword(params);
    }

    public int deleteByExKeywordId(long exkeyword_id){
        return externalKeywordDao.deleteByExKeywordId(exkeyword_id);
    }

    public int editExternalKeyword(Map<String,Object> params){
        return externalKeywordDao.editExtenalKeyword(params);
    }

    public int deleteByParams(Map<String, Object> params){
        return externalKeywordDao.deleteByParams(params);
    }
}
