package com.pkt.Service.User;

import com.pkt.Dao.User.UserPwdDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserPwdService {
    @Autowired
    UserPwdDao userPwdDao;
    public int add(Map<String, Object> params){
        return userPwdDao.add(params);
    }
    public Map<String, Object> queryList(Map<String, Object> params){
        return userPwdDao.queryList(params);
    }
    public int ChangePwd(Map<String, Object> params){
        return userPwdDao.ChangePwd(params);
    }
    public Map<String, Object> getByUserId(Long user_id){
        return userPwdDao.getByUserId(user_id);
    }
}
