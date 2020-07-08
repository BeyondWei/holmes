package com.shuzheng.holmes.common.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_hs_kafka_log_info")
public class THsKafkaLogInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * kafka连接地址
     */
    @Column(name = "kafka_url")
    private String kafkaUrl;

    /**
     * 主题
     */
    private String topic;

    /**
     * 分区号
     */
    private Integer partition;

    /**
     * 偏移位
     */
    private Long offset;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 是否有效：0有效，1无效
     */
    @Column(name = "is_effective")
    private Byte isEffective;

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
     * 获取kafka连接地址
     *
     * @return kafka_url - kafka连接地址
     */
    public String getKafkaUrl() {
        return kafkaUrl;
    }

    /**
     * 设置kafka连接地址
     *
     * @param kafkaUrl kafka连接地址
     */
    public void setKafkaUrl(String kafkaUrl) {
        this.kafkaUrl = kafkaUrl;
    }

    /**
     * 获取主题
     *
     * @return topic - 主题
     */
    public String getTopic() {
        return topic;
    }

    /**
     * 设置主题
     *
     * @param topic 主题
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * 获取分区号
     *
     * @return partition - 分区号
     */
    public Integer getPartition() {
        return partition;
    }

    /**
     * 设置分区号
     *
     * @param partition 分区号
     */
    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    /**
     * 获取偏移位
     *
     * @return offset - 偏移位
     */
    public Long getOffset() {
        return offset;
    }

    /**
     * 设置偏移位
     *
     * @param offset 偏移位
     */
    public void setOffset(Long offset) {
        this.offset = offset;
    }

    /**
     * 获取创建时间
     *
     * @return createtime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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
}