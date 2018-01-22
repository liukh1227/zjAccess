package com.zj.access.base.mapper;

import java.util.List;
import java.util.Map;

import com.zj.entity.base.po.User;
import com.zj.entity.bm.form.UserQueryForm;

public interface UserMapper  extends BaseMapper {
    
    public  List<User> getUserPageList(UserQueryForm form);
    
    public User getUserByAccount(Map<String,Object> params);
    
    public User getUserByIdOrAccountId(Map<String,Object> params);
    
    public User isValidUser(Map<String,Object> params);
}