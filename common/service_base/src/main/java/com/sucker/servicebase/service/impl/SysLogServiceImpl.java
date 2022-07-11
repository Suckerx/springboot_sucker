/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.sucker.servicebase.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sucker.servicebase.entity.SysLogEntity;
import com.sucker.servicebase.mapper.SysLogDao;
import com.sucker.servicebase.service.SysLogService;
import org.springframework.stereotype.Service;



@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {


}
