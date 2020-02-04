package com.pkt.Dao.RunTask;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface RunCaseDao {
    /**
     * 添加执行测试用例信息
     * @param params
     * @return
     */
    public int addRunCaseInfo(Map<String, Object> params);

    /**
     * 获取执行测试用例信息
     * @param params
     * @return
     */
    public Map<String, Object> getRunCaseInfoByParams(Map<String, Object> params);

}
