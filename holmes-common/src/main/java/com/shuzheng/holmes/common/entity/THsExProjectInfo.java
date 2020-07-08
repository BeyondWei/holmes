package com.shuzheng.holmes.common.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_hs_ex_project_info")
public class THsExProjectInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 项目主键
     */
    private String uuid;

    /**
     * 项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     * 备注
     */
    private String note;

    /**
     * 给项目分配的zk节点
     */
    @Column(name = "zk_node")
    private String zkNode;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 心跳时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否存活：0正常。1离线
     */
    @Column(name = "is_survive")
    private Byte isSurvive;

    /**
     * 心跳内容
     */
    private String context;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取项目主键
     *
     * @return uuid - 项目主键
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置项目主键
     *
     * @param uuid 项目主键
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取项目名称
     *
     * @return project_name - 项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 设置项目名称
     *
     * @param projectName 项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 获取备注
     *
     * @return note - 备注
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置备注
     *
     * @param note 备注
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 获取给项目分配的zk节点
     *
     * @return zk_node - 给项目分配的zk节点
     */
    public String getZkNode() {
        return zkNode;
    }

    /**
     * 设置给项目分配的zk节点
     *
     * @param zkNode 给项目分配的zk节点
     */
    public void setZkNode(String zkNode) {
        this.zkNode = zkNode;
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

    /**
     * 获取心跳时间
     *
     * @return update_time - 心跳时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置心跳时间
     *
     * @param updateTime 心跳时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否存活：0正常。1离线
     *
     * @return is_survive - 是否存活：0正常。1离线
     */
    public Byte getIsSurvive() {
        return isSurvive;
    }

    /**
     * 设置是否存活：0正常。1离线
     *
     * @param isSurvive 是否存活：0正常。1离线
     */
    public void setIsSurvive(Byte isSurvive) {
        this.isSurvive = isSurvive;
    }

    /**
     * 获取心跳内容
     *
     * @return context - 心跳内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置心跳内容
     *
     * @param context 心跳内容
     */
    public void setContext(String context) {
        this.context = context;
    }
}