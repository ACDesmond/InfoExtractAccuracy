package com.nju.infoextract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nju.infoextract.entity.AyEntity;
import com.nju.infoextract.entity.XxxEntity;
import com.nju.infoextract.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @author: songqiang
 * @since: 2020/12/15
 */
public interface AyService extends IService<AyEntity> {

    /**
     * <p>
     *     获取案由
     * </p>
     *
     * @param params
     * @return
     */
    PageUtils queryAyPage(Map<String, Object> params);

    /**
     * <p>
     *     添加案由项
     * </p>
     *
     * @param type
     * @param title
     * @param content
     */
    void addAy(String type, String title, String content);

    /**
     * <p>
     *     更新案由项
     * </p>
     *
     * @param id
     * @param type
     * @param title
     * @param content
     * @param sfbz
     * @param bzjg
     */
    void updateAy(Long id, String type, String title, String content, int sfbz, String bzjg);

    /**
     * <p>
     *     删除单条案由
     * </p>
     *
     * @param id
     */
    void deleteAy(Long id);



        boolean removeByIds(Long[] ids);

    List<XxxEntity> getXxxEntityListById(Long id);


    boolean updateAyXxx(Long id, List<XxxEntity> xxxEntityList);

}

