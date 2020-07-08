package com.shuzheng.holmes.service.plugin;

import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface BaseService<T> {

    T selectById(Object key);

    int save(T entity);

    int insertNotNull(T entity);

    int deleteById(Object key);

    int deleteByExample(Example example);

    int update(T entity);

    int updateNotNull(T entity);

    int updateByExampleSelective(T entity, Example example);

    List<T> selectByExample(Object example);

    Condition createCondition();

    PageInfo<T> findPage(Example example, Integer pageNum, Integer pageSize);

    /**
     * 保存一个list实体，null的属性不会保存，会使用数据库默认值
     *
     * @param list
     * @return
     */
    Integer batchAdd(List<T> list);

    int selectCountByExample(Object example);

    List<T> selectAll();

    /**
     * 根据主键更新属性不为null的值
     *
     * @param t
     * @return
     */
    Integer updateByPrimaryKeySelective(T t);

    /**
     * 根据主键更新全部属性
     *
     * @param t
     * @return
     */
    Integer updateByPrimaryKey(T t);

    /**
     * 根据主键更新属性不为null的值
     *
     * @param list
     * @return
     */
    Integer batchUpdateByPrimaryKey(List<T> list);


    List<T> select(T t);

    T selectOne(T t);

    /**
     * 剔除字段为空的信息进行搜索
     * @param t
     * @return
     */
    List<T> selectNotEmpty(T t);

    int delete(T t);

}
