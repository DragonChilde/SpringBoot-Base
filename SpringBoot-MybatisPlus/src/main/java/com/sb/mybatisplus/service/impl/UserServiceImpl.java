package com.sb.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sb.mybatisplus.bean.User;
import com.sb.mybatisplus.mapper.UserMapper;
import com.sb.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @title: UserServiceImpl
 * @Author Wen
 * @Date: 2021/2/4 11:51
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}