package com.pkt.Dao.Keyword;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface ExternalKeywordDao {
    /**
     * 通过exkeyword_id获取外部关键字信息
     * @param exkeyword_id
     * @return
     */
    public Map<String, Object> getByExKeywordId(long exkeyword_id);

    /**
     * 通过exkeyword_name获取外部关键字的信息
     * @param exkeyword_name
     * @return
     */
    public Map<String, Object> getExternalKeywordByName(String exkeyword_name);

    /**
     * 增加一个外部关键字信息
     * @param params
     * @return
     */
    public int addExtenalKeyword(Map<String,Object> params);

    /**
     * 通过exkeyword_id删除一个外部关键字信息
     * @param exkeyword_id
     * @return
     */
    public int deleteByExKeywordId(long exkeyword_id);

    /**
     * 更新外部关键字信息
     * @param params
     * @return
     */
    public int editExtenalKeyword(Map<String, Object> params);

    /**
     * 通过给定参数删除
     * @param params
     * @return
     */
    public int deleteByParams(Map<String, Object> params);

}
