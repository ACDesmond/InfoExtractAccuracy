package com.nju.infoextract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 案由配置表
 *
 * @author: songqiang
 * @since: 2021/1/5
 */
@Data
@TableName("ay_conf")
public class AyConfEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建案由配置项的用户
     */
    private String user;

    /**
     * 案由配置项的名称
     */
    private String name;

    /**
     * 信息项列表，用,隔开
     */
    private String xxx;
}
