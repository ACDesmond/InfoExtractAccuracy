package com.nju.infoextract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.entity.AyXxxEntity;

import java.util.Map;

/**
 * 案由_信息项关系表
 *
 * @author piaorongzhen
 * @email piaorongzhen@qq.com
 * @date 2020-04-20 17:02:36
 */
public interface AyXxxService extends IService<AyXxxEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

