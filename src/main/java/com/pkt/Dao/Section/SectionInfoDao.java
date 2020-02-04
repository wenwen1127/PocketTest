package com.pkt.Dao.Section;

import com.pkt.Entity.Section.Section;
import com.pkt.Entity.Section.SectionTree;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SectionInfoDao {

    /**
     * 通过section_id获取section信息
     * @param section_id
     * @return
     */
    public Map<String, Object> getBySectionId(int section_id);

    /**
     * 添加一个section
     * @param params
     * @return
     */
    public int addSection(Map<String, Object> params);

    /**
     * 通过section_id删除section
     * @param section_id
     * @return
     */
    public int deleteBySectionId(int section_id);

    /**
     * 修改Section
     * @param params
     * @return
     */
    public int editSection(Map<String, Object> params);

    /**
     * 查询所有的子部门
     * @param section_id
     * @return
     */
    public List<SectionTree> queryAllSections(int section_id);

    /**
     * 查询最高层部门信息
     * @return
     */
    public SectionTree findFirstSection();

//    public List<SectionTree> queryFullSectionById(int section_id);

}
