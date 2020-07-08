package com.shuzheng.holmes.service.plugin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuzheng.holmes.common.utils.StringUtils;
import com.shuzheng.holmes.dao.plugin.TkMybatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @Company 杭州数政科技有限公司
 * @Author 有成
 * @Date 2019-03-11 10:09
 * @Description
 * @Version 1.0
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    protected TkMybatisMapper<T> mapper;

    private Class<T> cls = null;

    public BaseServiceImpl() {
        Class clz = this.getClass();
        ParameterizedType type = (ParameterizedType) clz.getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        cls = (Class<T>) types[0];
    }

    @Override
    public T selectById(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int insertNotNull(T entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int deleteById(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int deleteByExample(Example example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int update(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int updateByExampleSelective(T entity, Example example) {
        return mapper.updateByExampleSelective(entity, example);
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param t
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateByPrimaryKeySelective(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 根据主键更新全部属性
     *
     * @param t
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateByPrimaryKey(T t) {
        return mapper.updateByPrimaryKey(t);
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param list
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer batchUpdateByPrimaryKey(List<T> list) {
        list.forEach(e -> {
            mapper.updateByPrimaryKeySelective(e);
        });
        return list.size();
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    @Override
    public Condition createCondition() {
        Condition condition = new Condition(cls);
        return condition;
    }

    /**
     * 根据实体中的属性值进行分页查询，查询条件使用等号
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<T> findPage(Example example, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum != null ? pageNum : 0, pageSize != null ? pageSize : 0);
        return new PageInfo(mapper.selectByExample(example));
    }

    /**
     * 保存一个list实体，null的属性不会保存，会使用数据库默认值
     *
     * @param list
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer batchAdd(List<T> list) {
        if (list.size() > 0) {
            mapper.insertList(list);
        }
        return 0;
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> select(T t) {
        return mapper.select(t);
    }

    @Override
    public T selectOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public List<T> selectNotEmpty(T t) {
        t = (T) StringUtils.paramToNull(t);
        return mapper.select(t);
    }

    @Override
    public int delete(T t) {
        return mapper.delete(t);
    }

}