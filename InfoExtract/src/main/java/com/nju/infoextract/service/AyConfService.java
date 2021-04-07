package com.nju.infoextract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nju.infoextract.entity.AyConfEntity;
import com.nju.infoextract.utils.PageUtils;

import java.util.Map;

/**
 * 案由配置
 *
 * @author: songqiang
 * @since: 2021/1/5
 */
public interface AyConfService extends IService<AyConfEntity> {
    /**
     * <p>
     *     查询案由配置
     * </p>
     *
     * @param username
     * @return
     */
    PageUtils queryAyConfPage(String username);

    /**
     * <p>
     *     添加新的案由配置
     * </p>
     *
     * @param user
     * @param name
     * @param xxx
     */
    void addAyConf(String user, String name, String xxx);

    /**
     * <p>
     *     编辑案由配置
     * </p>
     *
     * @param id
     * @param name
     * @param xxx
     */
    void editAyConf(Long id, String name, String xxx);

    /**
     * <p>
     *     删除某条案由配置
     * </p>
     *
     * @param id
     */
    void deleteAyConf(Long id);
}
