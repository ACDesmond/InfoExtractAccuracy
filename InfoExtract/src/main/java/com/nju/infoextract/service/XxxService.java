package com.nju.infoextract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nju.infoextract.entity.AyEntity;
import com.nju.infoextract.entity.ShEntity;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.entity.XxxEntity;
import org.dom4j.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 信息项
 *
 * @author piaorongzhen
 * @email piaorongzhen@qq.com
 * @date 2020-04-20 17:02:36
 */
public interface XxxService extends IService<XxxEntity> {

    /**
     * <p>
     *     查询信息项表
     * </p>
     *
     * @param params
     * @return
     */
    PageUtils queryXxxPage(Map<String, Object> params);

    /**
     * <p>
     *     审核通过，添加新的信息项、对应的子程序以及编译后的字节码
     * </p>
     *
     * @param shEntity
     * @param byteObj
     */
    void addXxxWithZcx(ShEntity shEntity, Byte[] byteObj);

    /**
     * <p>
     *     （信息项不是新建的）审核通过，在已有的信息项记录上添加抽取子程序源代码和字节码
     * </p>
     *
     * @param shEntity
     * @param byteObj
     */
    void addZcx(ShEntity shEntity, Byte[] byteObj);

    /**
     * <p>
     *     管理员添加信息项
     * </p>
     *
     * @param xxxmc
     * @param xxxsxjc
     * @param aytype
     */
    void addXxx(String xxxmc, String xxxsxjc, String aytype);

    /**
     * <p>
     *     管理员编辑信息项信息
     * </p>
     *
     * @param id
     * @param xxxmc
     * @param xxxsxjc
     * @param sfyycqzcx
     * @param aytype
     * @param zcxscz
     * @param wjm
     * @param ydm
     */
    void updateXxx(Long id, String xxxmc, String xxxsxjc, int sfyycqzcx,String aytype, String zcxscz, String wjm, String ydm);

    /**
     * <p>
     *     管理员删除某个信息项
     * </p>
     *
     * @param id
     */
    void deleteXxx(Long id);

    void storeAy(String text, String username);

    /**
     * <p>
     *     执行抽取
     * </p>
     *
     * @param text
     * @param username
     * @param xxxList
     */
    Document extract(String text, String username, ArrayList<String> xxxList);

//    boolean removeByIds(Long[] ids);
//
//    List<AyEntity> getAyEntityListById(Long id);
//
//    boolean addCqzcx(Long xxx_id, MultipartFile file) throws IOException;
//
//    boolean update(Long xxx_id, MultipartFile file) throws IOException;



}

