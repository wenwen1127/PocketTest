package com.pkt.Dao.RunTask;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface RunSuiteDao {
    /**
     * 添加执行测试用例信息
     * @param params
     * @return
     */
    public int addRunSuiteInfo(Map<String, Object> params);

    /**
     * 获取执行测试用例信息
     * @param params
     * @return
     */
    public List<Map<String, Object>> getRunSuiteInfoByParams(Map<String, Object> params);

    public List<Map<String, Object>> completedTask(String startDate);



}
