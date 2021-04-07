package com.nju.infoextract.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.nju.infoextract.common.compiler.CodeHelper;
import com.nju.infoextract.common.compiler.DynamicLoader;
import com.nju.infoextract.controller.tmp.MockDb;
import com.nju.infoextract.dao.*;
import com.nju.infoextract.entity.*;
import com.nju.infoextract.utils.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.utils.Query;

import com.nju.infoextract.service.XxxService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@Service("xxxService")
public class XxxServiceImpl extends ServiceImpl<XxxDao, XxxEntity> implements XxxService {


//    @Autowired
//    private AyXxxDao ayXxxDao;
//    @Autowired
//    private AyDao ayDao;
    @Resource
    private XxxDao xxxDao;

    @Override
    public void addXxxWithZcx(ShEntity shEntity, Byte[] byteObj) {
        XxxEntity entity = new XxxEntity();
        entity.setAytype(shEntity.getAytype());
        entity.setXxxmc(shEntity.getXxxname());
        entity.setXxxsxjc(shEntity.getXxxsx());
        entity.setZcxscz(shEntity.getUploader());
        entity.setSsaygs(0);
        entity.setSfyycqzcx(1);
        entity.setZcxscz(shEntity.getUploader());
        entity.setWjm(shEntity.getWjm());
        entity.setYdm(shEntity.getYdm());
        entity.setZjm(byteObj);
        xxxDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addZcx(ShEntity shEntity, Byte[] byteObj) {
        XxxEntity entity = xxxDao.selectOne(new QueryWrapper<XxxEntity>().
                select("id", "sfyycqzcx", "wjm", "ydm", "zjm").
                eq("xxxmc", shEntity.getXxxmc()));
        //不论信息项有没有子程序，直接用新来的子程序覆盖
        entity.setZcxscz(shEntity.getUploader());
        entity.setSfyycqzcx(1);
        entity.setWjm(shEntity.getWjm());
        entity.setYdm(shEntity.getYdm());
        entity.setZjm(byteObj);

        xxxDao.updateById(entity);
//        if (entity.getSfyycqzcx() == 0){
//            //原来的xxx无抽取子程序，直接添加更新
//            entity.setSfyycqzcx(1);
//            entity.setWjm(shEntity.getWjm());
//            entity.setYdm(shEntity.getYdm());
//            entity.setZjm(byteObj);
//            xxxDao.updateById(entity);
//        }else {
//            //todo 更新策略应该按照提取子程序的抽取准确率进行更新
//        }

    }

    @Override
    public void addXxx(String xxxmc, String xxxsxjc, String aytype) {
        XxxEntity entity = new XxxEntity();
        entity.setXxxmc(xxxmc);
        entity.setSsaygs(1);
        entity.setXxxsxjc(xxxsxjc);
        entity.setAytype(aytype);
        xxxDao.insert(entity);
    }

    @Override
    public void updateXxx(Long id, String xxxmc, String xxxsxjc, int sfyycqzcx, String aytype, String zcxscz, String wjm, String ydm) {
        XxxEntity entity = new XxxEntity();
        entity.setId(id);
        entity.setXxxmc(xxxmc);
        entity.setXxxsxjc(xxxsxjc);
        entity.setSfyycqzcx(sfyycqzcx);
        entity.setAytype(aytype);
        entity.setZcxscz(zcxscz);
        entity.setWjm(wjm);
        entity.setYdm(ydm);
        xxxDao.updateById(entity);
    }

    @Override
    public void deleteXxx(Long id) {
        xxxDao.deleteById(id);
    }

    @Override
    public void storeAy(String text, String username){
        MockDb.aystore.put(username, text);
    }

    /*
    <BGKBLY>
     */
    @Override
    public Document extract(String text, String username, ArrayList<String> xxxList) {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("list");
        for (String xxxmc : xxxList){
            XxxEntity entity = xxxDao.selectOne(new QueryWrapper<XxxEntity>().
                    select("xxxsxjc","wjm", "zjm").
                    eq("xxxmc", xxxmc));
            String sxjc = entity.getXxxsxjc();
            String wjm = entity.getWjm();
            Byte[] byteObj = entity.getZjm();

            Element element = CodeHelper.invokeExtract(text, wjm, byteObj);

            root.add(element);
        }
        return doc;
    }

    //    // 通过xxx_id添加cqzcx
//    @Transactional
//    @Override
//    public boolean addCqzcx(Long xxx_id, MultipartFile file) throws IOException {
//2
//
//        // 获取JavaCode
//        String JavaCode = FileUtils.getContentByFile(file);
//        // 编译JavaCode
//        Map<String, byte[]> bytecode = DynamicLoader.compile("Extract.java", JavaCode);
//        // 获取字节码
//        byte[] classbyte = bytecode.get("Extract");
//        Byte[] class_byte = ArrayUtils.toObject(classbyte);
//
//        // 更新xxx
//        XxxEntity xxxEntity = xxxDao.selectById(xxx_id);
//        xxxEntity.setSfyycqzcx(1);
//
//        xxxEntity.setWjm("Extract.java");
//        xxxEntity.setYdm(JavaCode);
//        xxxEntity.setZjm(class_byte);
//        xxxDao.updateById(xxxEntity);
//
//        return true;
//    }

//    @Override
//    public boolean update(Long xxx_id, MultipartFile file) throws IOException {
//        // 获取JavaCode
//        String JavaCode = FileUtils.getContentByFile(file);
//        // 编译JavaCode
//        Map<String, byte[]> bytecode = DynamicLoader.compile("Extract.java", JavaCode);
//        // 获取字节码
//        byte[] classbyte = bytecode.get("Extract");
//        Byte[] class_byte = ArrayUtils.toObject(classbyte);
//
//        // 获得xxxEntity
//        XxxEntity xxxEntity = xxxDao.selectById(xxx_id);
//        // 设置xxxEntity
//        xxxEntity.setYdm(JavaCode);
//        xxxEntity.setZjm(class_byte);
//        // 更新cqzcxEntity
//        xxxDao.updateById(xxxEntity);
//
//
//        return true;
//    }




    @Override
    public PageUtils queryXxxPage(Map<String, Object> params) {
        IPage<XxxEntity> page = this.page(
                new Query<XxxEntity>().getPage(params),
                new QueryWrapper<XxxEntity>().select("id", "xxxmc", "xxxsxjc", "ssaygs", "aytype","sfyycqzcx","zcxscz", "wjm", "ydm")
        );

        return new PageUtils(page);
    }

//    // 删除信息项
//    @Override
//    @Transactional
//    public boolean removeByIds(Long[] ids) {
//
////        for (Long id : ids) {
////
////            // 通过xxx_id查找ayXxxEntityList
////            QueryWrapper<AyXxxEntity> queryWrapper_AyXxxEntity = new QueryWrapper<>();
////            queryWrapper_AyXxxEntity.eq("xxx_id", id);
////            List<AyXxxEntity> ayXxxEntityList = ayXxxDao.selectList(queryWrapper_AyXxxEntity);
////
////            // 遍历ayXxxEntityList
////            for (AyXxxEntity ayXxxEntity : ayXxxEntityList) {
////                // 查找并更新ay表
////                QueryWrapper<AyEntity> queryWrapper_AyEntity = new QueryWrapper<>();
////                queryWrapper_AyEntity.eq("id", ayXxxEntity.getAyId());
////                AyEntity ayEntity = ayDao.selectOne(queryWrapper_AyEntity);
////                ayEntity.setYyxxxgs(ayEntity.getYyxxxgs() - 1);
////                ayDao.updateById(ayEntity);
////            }
////
////            // 更新ay_xxx表
////            UpdateWrapper<AyXxxEntity> updateWrapper_AyXxxEntity = new UpdateWrapper<>();
////            updateWrapper_AyXxxEntity.eq("xxx_id", id);
////            ayXxxDao.delete(updateWrapper_AyXxxEntity);
////        }
////
////        // 删除xxx
////        xxxDao.deleteBatchIds(Arrays.asList(ids));
//
//        return true;
//    }

//    // 通过xxx_id获取AyEntityList
//    @Override
//    public List<AyEntity> getAyEntityListById(Long id) {
//
//        QueryWrapper<AyXxxEntity> queryWrapper_AyXxxEntity = new QueryWrapper<>();
//        queryWrapper_AyXxxEntity.select("ay_id").eq("xxx_id", id);
//        List<AyXxxEntity> ayXxxEntityList = ayXxxDao.selectList(queryWrapper_AyXxxEntity);
//
//        List<AyEntity> ayEntityList = new ArrayList<>(ayXxxEntityList.size());
//        for (AyXxxEntity ayXxxEntity : ayXxxEntityList) {
//            QueryWrapper<AyEntity> queryWrapper_AyEntity = new QueryWrapper<>();
//            queryWrapper_AyEntity.select("id", "aymc").eq("id", ayXxxEntity.getAyId());
//            AyEntity ayEntity = ayDao.selectOne(queryWrapper_AyEntity);
//            ayEntityList.add(ayEntity);
//        }
//
//
//        return ayEntityList;
//    }

}