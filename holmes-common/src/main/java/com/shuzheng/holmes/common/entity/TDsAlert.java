package com.shuzheng.holmes.common.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_ds_alert")
public class TDsAlert {
    /**
     * key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * title
     */
    private String title;

    /**
     * send email type,0:TABLE,1:TEXT
     */
    @Column(name = "show_type")
    private Byte showType;

    /**
     * 0:email,1:sms
     */
    @Column(name = "alert_type")
    private Byte alertType;

    /**
     * 0:wait running,1:success,2:failed
     */
    @Column(name = "alert_status")
    private Byte alertStatus;

    /**
     * alert group id
     */
    @Column(name = "alertgroup_id")
    private Integer alertgroupId;

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
     * Message content (can be email, can be SMS. Mail is stored in JSON map, and SMS is string)
     */
    private String content;

    /**
     * log
     */
    private String log;

    /**
     * receivers
     */
    private String receivers;

    /**
     * cc
     */
    @Column(name = "receivers_cc")
    private String receiversCc;

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
     * 获取title
     *
     * @return title - title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title
     *
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取send email type,0:TABLE,1:TEXT
     *
     * @return show_type - send email type,0:TABLE,1:TEXT
     */
    public Byte getShowType() {
        return showType;
    }

    /**
     * 设置send email type,0:TABLE,1:TEXT
     *
     * @param showType send email type,0:TABLE,1:TEXT
     */
    public void setShowType(Byte showType) {
        this.showType = showType;
    }

    /**
     * 获取0:email,1:sms
     *
     * @return alert_type - 0:email,1:sms
     */
    public Byte getAlertType() {
        return alertType;
    }

    /**
     * 设置0:email,1:sms
     *
     * @param alertType 0:email,1:sms
     */
    public void setAlertType(Byte alertType) {
        this.alertType = alertType;
    }

    /**
     * 获取0:wait running,1:success,2:failed
     *
     * @return alert_status - 0:wait running,1:success,2:failed
     */
    public Byte getAlertStatus() {
        return alertStatus;
    }

    /**
     * 设置0:wait running,1:success,2:failed
     *
     * @param alertStatus 0:wait running,1:success,2:failed
     */
    public void setAlertStatus(Byte alertStatus) {
        this.alertStatus = alertStatus;
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
     * 获取Message content (can be email, can be SMS. Mail is stored in JSON map, and SMS is string)
     *
     * @return content - Message content (can be email, can be SMS. Mail is stored in JSON map, and SMS is string)
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置Message content (can be email, can be SMS. Mail is stored in JSON map, and SMS is string)
     *
     * @param content Message content (can be email, can be SMS. Mail is stored in JSON map, and SMS is string)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取log
     *
     * @return log - log
     */
    public String getLog() {
        return log;
    }

    /**
     * 设置log
     *
     * @param log log
     */
    public void setLog(String log) {
        this.log = log;
    }

    /**
     * 获取receivers
     *
     * @return receivers - receivers
     */
    public String getReceivers() {
        return receivers;
    }

    /**
     * 设置receivers
     *
     * @param receivers receivers
     */
    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    /**
     * 获取cc
     *
     * @return receivers_cc - cc
     */
    public String getReceiversCc() {
        return receiversCc;
    }

    /**
     * 设置cc
     *
     * @param receiversCc cc
     */
    public void setReceiversCc(String receiversCc) {
        this.receiversCc = receiversCc;
    }
}