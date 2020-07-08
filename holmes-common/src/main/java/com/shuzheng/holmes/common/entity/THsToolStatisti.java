package com.shuzheng.holmes.common.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_hs_tool_statisti")
public class THsToolStatisti {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 处理器主键
     */
    @Column(name = "deal_uuid")
    private String dealUuid;

    /**
     * 统计字段
     */
    private String field;

    /**
     * 次数
     */
    private Integer count;

    /**
     * 时间段，单位为秒
     */
    private Integer period;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取自增主键
     *
     * @return id - 自增主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增主键
     *
     * @param id 自增主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取处理器主键
     *
     * @return deal_uuid - 处理器主键
     */
    public String getDealUuid() {
        return dealUuid;
    }

    /**
     * 设置处理器主键
     *
     * @param dealUuid 处理器主键
     */
    public void setDealUuid(String dealUuid) {
        this.dealUuid = dealUuid;
    }

    /**
     * 获取统计字段
     *
     * @return field - 统计字段
     */
    public String getField() {
        return field;
    }

    /**
     * 设置统计字段
     *
     * @param field 统计字段
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * 获取次数
     *
     * @return count - 次数
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置次数
     *
     * @param count 次数
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取时间段，单位为秒
     *
     * @return period - 时间段，单位为秒
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * 设置时间段，单位为秒
     *
     * @param period 时间段，单位为秒
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}