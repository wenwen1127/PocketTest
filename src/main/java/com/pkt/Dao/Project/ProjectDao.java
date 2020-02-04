package com.pkt.Dao.Project;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface ProjectDao {

    /**
     * 通过project_id获取project信息
     * @param project_id
     * @return
     */
    public Map<String, Object> getByProjectId(int project_id);

    /**
     * 增加project
     * @param params
     * @return
     */
    public int addProject(Map<String, Object> params);

    /**
     * 通过project_id删除project信息
     * @param project_id
     * @return
     */
    public int deleteByProjectId(int project_id);

    /**
     * 修改project_info
     * @param params
     * @return
     */
    public int editProject(Map<String, Object> params);

}
