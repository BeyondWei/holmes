package com.shuzheng.holmes.common.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_hs_filter_rules_info")
public class THsFilterRulesInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 过滤规则组uuid
     */
    @Column(name = "filter_group_uuid")
    private String filterGroupUuid;

    /**
     * 处理规则组uuid
     */
    @Column(name = "deal_group_uuid")
    private String dealGroupUuid;

    /**
     * 规则uuid
     */
    private String uuid;

    /**
     * 规则名称
     */
    @Column(name = "rules_name")
    private String rulesName;

    /**
     * 备注
     */
    private String note;

    /**
     * 规则类型（java，shell）
     */
    private String type;

    /**
     * 过滤规则，json格式
     */
    private String config;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否有效：0有效，1无效
     */
    @Column(name = "is_effective")
    private Byte isEffective;

    /**
     * 过滤详细内容
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
     * 获取过滤规则组uuid
     *
     * @return filter_group_uuid - 过滤规则组uuid
     */
    public String getFilterGroupUuid() {
        return filterGroupUuid;
    }

    /**
     * 设置过滤规则组uuid
     *
     * @param filterGroupUuid 过滤规则组uuid
     */
    public void setFilterGroupUuid(String filterGroupUuid) {
        this.filterGroupUuid = filterGroupUuid;
    }

    /**
     * 获取处理规则组uuid
     *
     * @return deal_group_uuid - 处理规则组uuid
     */
    public String getDealGroupUuid() {
        return dealGroupUuid;
    }

    /**
     * 设置处理规则组uuid
     *
     * @param dealGroupUuid 处理规则组uuid
     */
    public void setDealGroupUuid(String dealGroupUuid) {
        this.dealGroupUuid = dealGroupUuid;
    }

    /**
     * 获取规则uuid
     *
     * @return uuid - 规则uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置规则uuid
     *
     * @param uuid 规则uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取规则名称
     *
     * @return rules_name - 规则名称
     */
    public String getRulesName() {
        return rulesName;
    }

    /**
     * 设置规则名称
     *
     * @param rulesName 规则名称
     */
    public void setRulesName(String rulesName) {
        this.rulesName = rulesName;
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
     * 获取规则类型（java，shell）
     *
     * @return type - 规则类型（java，shell）
     */
    public String getType() {
        return type;
    }

    /**
     * 设置规则类型（java，shell）
     *
     * @param type 规则类型（java，shell）
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取过滤规则，json格式
     *
     * @return config - 过滤规则，json格式
     */
    public String getConfig() {
        return config;
    }

    /**
     * 设置过滤规则，json格式
     *
     * @param config 过滤规则，json格式
     */
    public void setConfig(String config) {
        this.config = config;
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
     * 获取过滤详细内容
     *
     * @return context - 过滤详细内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置过滤详细内容
     *
     * @param context 过滤详细内容
     */
    public void setContext(String context) {
        this.context = context;
    }
}