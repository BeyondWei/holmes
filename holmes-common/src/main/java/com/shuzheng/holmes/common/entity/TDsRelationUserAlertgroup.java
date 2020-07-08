package com.shuzheng.holmes.common.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_ds_relation_user_alertgroup")
public class TDsRelationUserAlertgroup {
    /**
     * key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * alert group id
     */
    @Column(name = "alertgroup_id")
    private Integer alertgroupId;

    /**
     * user id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * create time
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * update time
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取key
     *
     * @return id - key
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置key
     *
     * @param id key
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取alert group id
     *
     * @return alertgroup_id - alert group id
     */
    public Integer getAlertgroupId() {
        return alertgroupId;
    }

    /**
     * 设置alert group id
     *
     * @param alertgroupId alert group id
     */
    public void setAlertgroupId(Integer alertgroupId) {
        this.alertgroupId = alertgroupId;
    }

    /**
     * 获取user id
     *
     * @return user_id - user id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置user id
     *
     * @param userId user id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取create time
     *
     * @return create_time - create time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置create time
     *
     * @param createTime create time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取update time
     *
     * @return update_time - update time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置update time
     *
     * @param updateTime update time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}