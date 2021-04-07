package com.nju.infoextract.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * 信息项
 * 
 * @author piaorongzhen
 * @email piaorongzhen@qq.com
 * @date 2020-04-20 17:02:36
 */
@Data
@TableName("xxx")
public class XxxEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 信息项名称
	 */
	private String xxxmc;
	/**
	 * 信息项缩写简称
	 */
	private String xxxsxjc;
	/**
	 * 所属案由个数
	 */
	private Integer ssaygs;

	/**
	 * 属于哪类案由类型
	 */

	private String aytype;
	/**
	 * 是否拥有抽取子程序，1表示是，0表示否
	 */
	private Integer sfyycqzcx;

	/**
	 * 子程序上传者
	 */
	private String zcxscz;

	/**
	 * Java文件名
	 */
	private String wjm;

	/**
	 * Java源代码
	 */
	private String ydm;
	/**
	 * Java字节码
	 */
	private Byte[] zjm;

}
