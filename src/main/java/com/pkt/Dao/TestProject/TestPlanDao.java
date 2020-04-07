package com.pkt.Dao.TestProject;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
@Repository
public interface TestPlanDao {
    /**
     * 通过testplan_id获取测试套件信息
     * @param testplan_id
     * @return
     */

    public Map<String, Object> getByTestPlanId(long testplan_id);

    /**
     * 增加一个测试套件信息
     * @param params
     * @return
     */
    public int addTestPlan(Map<String,Object> params);

    /**
     * 通过testplan_id删除一个测试套件信息
     * @param testplan_id
     * @return
     */
    public int editTestPlan(long testplan_id);

}
