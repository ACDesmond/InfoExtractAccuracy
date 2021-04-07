package com.nju.infoextract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * 案由_信息项关系表
 *
 * @author: songqiang
 * @since: 2020/12/9
 */
@Data
@TableName("ay_xxx")
public class AyXxxEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 案由表id主键
	 */
	@TableField(value = "ay_id")
	private Long ayId;
	/**
	 * 信息项表id主键
	 */
	@TableField(value = "xxx_id")
	private Long xxxId;

}
