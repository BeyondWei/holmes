package com.shuzheng.holmes.common.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_hs_ex_flume_source_info")
public class THsExFlumeSourceInfo {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 一个项目和一个flume相互绑定
     */
    @Column(name = "project_uuid")
    private String projectUuid;

    /**
     * 日志的类型，与flume下的一个source相互绑定
     */
    @Column(name = "log_uuid")
    private String logUuid;

    /**
     * 日志类型说明
     */
    private String note;

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
     * 获取一个项目和一个flume相互绑定
     *
     * @return project_uuid - 一个项目和一个flume相互绑定
     */
    public String getProjectUuid() {
        return projectUuid;
    }

    /**
     * 设置一个项目和一个flume相互绑定
     *
     * @param projectUuid 一个项目和一个flume相互绑定
     */
    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    /**
     * 获取日志的类型，与flume下的一个source相互绑定
     *
     * @return log_uuid - 日志的类型，与flume下的一个source相互绑定
     */
    public String getLogUuid() {
        return logUuid;
    }

    /**
     * 设置日志的类型，与flume下的一个source相互绑定
     *
     * @param logUuid 日志的类型，与flume下的一个source相互绑定
     */
    public void setLogUuid(String logUuid) {
        this.logUuid = logUuid;
    }

    /**
     * 获取日志类型说明
     *
     * @return note - 日志类型说明
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置日志类型说明
     *
     * @param note 日志类型说明
     */
    public void setNote(String note) {
        this.note = note;
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