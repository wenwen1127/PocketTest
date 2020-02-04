package com.pkt.Dao.Keyword;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface FunctionDao {
    /**
     * 通过function_id获取函数信息
     * @param function_id
     * @return
     */
    public Map<String, Object> getByFunctionId(long function_id);

    /**
     * 通过function_name获取函数信息
     * @param function_name
     * @return
     */
    public Map<String, Object> getFunctionByName(String function_name);

    /**
     * 增加一个函数信息
     * @param params
     * @return
     */
    public int addFunction(Map<String,Object> params);

    /**
     * 通过tag_id删除一个函数信息
     * @param function_id
     * @return
     */
    public int deleteByFunctionId(long function_id);

    /**
     * 更新函数信息
     * @param params
     * @return
     */
    public int editFunction(Map<String, Object> params);

    /**
     * 通过给定参数删除
     * @param params
     * @return
     */
    public int deleteByParams(Map<String, Object> params);

}
