package com.pkt.Dao.TestProject;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SuiteTestpointDao {
    /**
     * 通过testpoint_id获取测试套件信息
     * @param suite_id
     * @return
     */

    public List<Long> getBySuiteId(long suite_id);

    /**
     * 增加一个测试套件信息
     * @param params
     * @return
     */
    public int add(Map<String,Object> params);

    /**
     *
     * @param suite_id
     * @return
     */
    public int count(long suite_id);
}
