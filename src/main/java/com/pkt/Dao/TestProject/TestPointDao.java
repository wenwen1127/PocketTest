package com.pkt.Dao.TestProject;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface TestPointDao {
    /**
     * 通过testpoint_id获取测试套件信息
     * @param testpoint_id
     * @return
     */

    public Map<String, Object> getByTestPointId(long testpoint_id);

    /**
     * 增加一个测试套件信息
     * @param params
     * @return
     */
    public int addTestPoint(Map<String,Object> params);

    /**
     * 通过testplan_id删除一个测试套件信息
     * @param testpoint_id
     * @return
     */
    public int editTestPoint(long testpoint_id);
}
