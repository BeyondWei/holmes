package com.shuzheng.holmes.common.entity;

import javax.persistence.*;

@Table(name = "t_hs_ex_rel_user_project")
public class THsExRelUserProject {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 项目主键
     */
    @Column(name = "project_uuid")
    private String projectUuid;

    /**
     * 权限
     */
    private String permis;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取项目主键
     *
     * @return project_uuid - 项目主键
     */
    public String getProjectUuid() {
        return projectUuid;
    }

    /**
     * 设置项目主键
     *
     * @param projectUuid 项目主键
     */
    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    /**
     * 获取权限
     *
     * @return permis - 权限
     */
    public String getPermis() {
        return permis;
    }

    /**
     * 设置权限
     *
     * @param permis 权限
     */
    public void setPermis(String permis) {
        this.permis = permis;
    }
}