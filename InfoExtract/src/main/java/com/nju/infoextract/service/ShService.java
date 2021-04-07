package com.nju.infoextract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nju.infoextract.entity.ShEntity;
import com.nju.infoextract.utils.PageUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author: songqiang
 * @since: 2020/11/30
 */
public interface ShService extends IService<ShEntity> {
    /**
     * <p>
     *     查询信息项审核表
     * </p>
     *
     * @param params
     * @return
     */
    PageUtils queryShPage(Map<String, Object> params);

    /**
     * <p>
     *     上传某个信息项的抽取子程序
     *     name和sx不为空时创建新的信息项
     *     提交到审核表
     * </p>
     *
     * @param file
     * @param xxx
     * @param name
     * @param sx
     * @param uploader
     * @return
     * @throws IOException
     */
    boolean addShZcx(MultipartFile file, String ayType, String xxx, String name, String sx, String uploader) throws IOException;

    /**
     * <p>
     *     更新审核表的状态，并返回审核对象
     * </p>
     *
     *
     * @param id 主键id
     * @param status 审核后的状态
     * @param comment 备注
     * @return
     */
    ShEntity getShItem(Long id, int status, String comment);

    /**
     * <p>
     *     更新审核表的item
     * </p>
     *
     * @param id
     * @param status
     * @param comment
     */
    void updateShItem(Long id, int status, String comment);

}
