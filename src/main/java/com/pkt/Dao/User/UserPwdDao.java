package com.pkt.Dao.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface UserPwdDao {
    /**
     * 添加 password
     * @param params
     * @return
     */
    public int add(Map<String, Object> params);

    /**
     * 查询password 并返回一个password字符串
     * @param params
     * @return
     */
    public Map<String,Object> queryList(Map<String, Object> params);

    /**
     * 修改密码
     * @param params
     * @return
     */
    public int ChangePwd(Map<String, Object> params);

    /**
     * 通过user_id获取password
     * @param user_id
     * @return
     */
    public Map<String, Object> getByUserId(Long user_id);
}
