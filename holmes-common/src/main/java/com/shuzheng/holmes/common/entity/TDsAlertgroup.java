package com.shuzheng.holmes.common.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_ds_alertgroup")
public class TDsAlertgroup {
    /**
     * key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * group name
     */
    @Column(name = "group_name")
    private String groupName;

    /**
     * Group type (message 0, SMS 1...)
     */
    @Column(name = "group_type")
    private Byte groupType;

    private String description;

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
     * 获取group name
     *
     * @return group_name - group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置group name
     *
     * @param groupName group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 获取Group type (message 0, SMS 1...)
     *
     * @return group_type - Group type (message 0, SMS 1...)
     */
    public Byte getGroupType() {
        return groupType;
    }

    /**
     * 设置Group type (message 0, SMS 1...)
     *
     * @param groupType Group type (message 0, SMS 1...)
     */
    public void setGroupType(Byte groupType) {
        this.groupType = groupType;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
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