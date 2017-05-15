package com.rujianbin.common.web.security.dao;



import com.rujianbin.common.web.security.entity.AuthorityEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 汝建斌 on 2017/4/1.
 */
@Mapper
public interface IAuthorityDao {

    public List<AuthorityEntity> getAuthorityEntityList(Long userId);
}
