package com.rujianbin.common.web.security.dao;


import com.rujianbin.common.web.security.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by 汝建斌 on 2017/4/1.
 */
@Mapper
public interface IUserDao {

    public UserEntity findUser(String username);
}
