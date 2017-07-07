package com.rujianbin.common.web.security;


import com.rujianbin.common.web.security.dao.IAuthorityDao;
import com.rujianbin.common.web.security.dao.IUserDao;
import com.rujianbin.principal.api.entity.AuthorityEntity;
import com.rujianbin.principal.api.entity.UserEntity;
import com.rujianbin.principal.api.security.RjbSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by 汝建斌 on 2017/4/1.
 */

public class RjbUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IAuthorityDao authorityDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findUser(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("UserName " + username + " not found");
        }
        List<AuthorityEntity> AuthorityEntityList = authorityDao.getAuthorityEntityList(userEntity.getId());
        userEntity.setAuthorityEntityList(AuthorityEntityList);
        RjbSecurityUser seu = new RjbSecurityUser(userEntity);
        return  seu;
    }
}
