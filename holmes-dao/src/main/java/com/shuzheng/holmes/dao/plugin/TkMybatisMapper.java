package com.shuzheng.holmes.dao.plugin;


import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface TkMybatisMapper<T> extends
        InsertListMapper<T>,
        tk.mybatis.mapper.common.BaseMapper<T>,
        Mapper<T>,
        IdsMapper<T>,
        MySqlMapper<T> {
}
