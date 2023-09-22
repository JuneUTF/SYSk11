package kk.soft.co.jp.sys.impl;

import kk.soft.co.jp.sys.mapper.UserMapper;
import kk.soft.co.jp.sys.model.UserModel;
import kk.soft.co.jp.sys.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceIml implements UserService {

    @Resource
    private UserMapper mapper;

    /**
     * 新規作成
     *
     * @param user
     */
    @Override
    public Object insert(UserModel user) {

        return mapper.insert(user);
    }
    @Override
    public Object setNewPassword(UserModel pass){
        return mapper.setNewPassword(pass);
    }
    // @Override
    // public UserModel selectpass(UserModel pass){
    //     return mapper.selectpass(pass);
    // }
    @Override
    public Object updatepass(UserModel pass){
        return mapper.updatepass(pass);
    }
}
