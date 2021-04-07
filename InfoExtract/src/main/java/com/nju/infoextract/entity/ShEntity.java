package com.nju.infoextract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 子程序审核表
 *
 * @author: songqiang
 * @since: 2020/11/23
 */
@Data
@TableName("sh")
public class ShEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否新建信息项，1表示是，0表示否，默认是0
     */
    private Integer newxxx;


    /**
     * 当前前端选中的信息项名称
     */
    private String xxxmc;

    /**
     * 新建的信息项名称
     */
    private String xxxname;

    /**
     * 新建的信息项缩写
     */
    private String xxxsx;

    /**
     * 属于哪类案由类型
     */
    private String aytype;

    /**
     * 上传该子程序的用户名
     */
    private String uploader;

    /**
     * Java文件名
     */
    private String wjm;

    /**
     * Java源代码
     */
    private String ydm;

    /**
     * 审核状态
     *      0: 待审核
     *      1：审核通过
     *      2：审核不通过
     */
    private Integer status;

    /**
     * 管理员审核备注
     */
    private String comment;
}
