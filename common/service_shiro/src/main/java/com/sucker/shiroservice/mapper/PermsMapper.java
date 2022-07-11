package com.sucker.shiroservice.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sucker.shiroservice.entity.Perms;
import com.sucker.shiroservice.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @Author 大誌
 * @Date 2019/3/30 22:05
 * @Version 1.0
 */
@Mapper
@Repository
public interface PermsMapper extends BaseMapper<Perms> {


}
