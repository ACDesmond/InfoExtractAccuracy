package com.nju.infoextract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nju.infoextract.entity.AyInfoEntity;
import com.nju.infoextract.utils.PageUtils;

import java.util.Map;

/**
 * @author: songqiang
 * @since: 2020/12/15
 */
public interface AyInfoService extends IService<AyInfoEntity> {

    /**
     * <p>
     *     查询案由信息表
     * </p>
     *
     * @param params
     * @return
     */
    PageUtils queryAyInfoPage(Map<String, Object> params);

    /**
     * <p>
     *     添加案由信息
     * </p>
     *
     * @param newAymc
     * @return
     */
    boolean addAyInfo(String newAymc);

    /**
     * <p>
     *     更新某条案由信息
     * </p>
     *
     * @param id
     * @param type
     * @param yyxxxgs
     */
    void updateAyInfo(Long id, String type, int yyxxxgs);
}
