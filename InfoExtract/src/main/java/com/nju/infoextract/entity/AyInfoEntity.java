package com.nju.infoextract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 案由信息表
 *
 * @author: songqiang
 * @since: 2020/12/9
 */
@Data
@TableName("ay_info")
public class AyInfoEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 案由所属类别
     */
    private String type;

    /**
     * 该类案由拥有信息项个数，默认为0
     */
    private Integer yyxxxgs;
}
