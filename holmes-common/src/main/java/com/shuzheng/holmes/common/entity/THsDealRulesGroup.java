package com.shuzheng.holmes.common.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_hs_deal_rules_group")
public class THsDealRulesGroup {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 规则组ID
     */
    private String uuid;

    /**
     * 规则组名称
     */
    @Column(name = "group_name")
    private String groupName;

    /**
     * 规则组说明
     */
    private String note;

    /**
     * 是否有效：0有效，1无效
     */
    @Column(name = "is_effective")
    private Byte isEffective;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取规则组ID
     *
     * @return uuid - 规则组ID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置规则组ID
     *
     * @param uuid 规则组ID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取规则组名称
     *
     * @return group_name - 规则组名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置规则组名称
     *
     * @param groupName 规则组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 获取规则组说明
     *
     * @return note - 规则组说明
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置规则组说明
     *
     * @param note 规则组说明
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 获取是否有效：0有效，1无效
     *
     * @return is_effective - 是否有效：0有效，1无效
     */
    public Byte getIsEffective() {
        return isEffective;
    }

    /**
     * 设置是否有效：0有效，1无效
     *
     * @param isEffective 是否有效：0有效，1无效
     */
    public void setIsEffective(Byte isEffective) {
        this.isEffective = isEffective;
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