package com.shuzheng.holmes.common.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_ds_user")
public class TDsUser {
    /**
     * user id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * user name
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * user password
     */
    @Column(name = "user_password")
    private String userPassword;

    /**
     * user type, 0:administrator，1:ordinary user
     */
    @Column(name = "user_type")
    private Byte userType;

    /**
     * email
     */
    private String email;

    /**
     * phone
     */
    private String phone;

    /**
     * tenant id
     */
    @Column(name = "tenant_id")
    private Integer tenantId;

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
     * queue
     */
    private String queue;

    /**
     * state 0:disable 1:enable
     */
    private Integer state;

    /**
     * 获取user id
     *
     * @return id - user id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置user id
     *
     * @param id user id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取user name
     *
     * @return user_name - user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置user name
     *
     * @param userName user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取user password
     *
     * @return user_password - user password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 设置user password
     *
     * @param userPassword user password
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * 获取user type, 0:administrator，1:ordinary user
     *
     * @return user_type - user type, 0:administrator，1:ordinary user
     */
    public Byte getUserType() {
        return userType;
    }

    /**
     * 设置user type, 0:administrator，1:ordinary user
     *
     * @param userType user type, 0:administrator，1:ordinary user
     */
    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    /**
     * 获取email
     *
     * @return email - email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置email
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取phone
     *
     * @return phone - phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置phone
     *
     * @param phone phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取tenant id
     *
     * @return tenant_id - tenant id
     */
    public Integer getTenantId() {
        return tenantId;
    }

    /**
     * 设置tenant id
     *
     * @param tenantId tenant id
     */
    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
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

    /**
     * 获取queue
     *
     * @return queue - queue
     */
    public String getQueue() {
        return queue;
    }

    /**
     * 设置queue
     *
     * @param queue queue
     */
    public void setQueue(String queue) {
        this.queue = queue;
    }

    /**
     * 获取state 0:disable 1:enable
     *
     * @return state - state 0:disable 1:enable
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置state 0:disable 1:enable
     *
     * @param state state 0:disable 1:enable
     */
    public void setState(Integer state) {
        this.state = state;
    }
}