package com.nju.infoextract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 案由表
 *
 * @author: songqiang
 * @since: 2020/12/9
 */
@Data
@TableName("ay")
public class AyEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 案由类型，对应an_info的一个类型
     */
    private String type;

    /**
     * 案由的标题
     */
    private String title;

    /**
     * 案由文本内容
     */
    private String content;

    /**
     * 是否标注，1表示是，0表示否，默认是0
     */
    private Integer sfbz;

    /**
     * 标注结果
     */
    private String bzjg;
}
