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
        //?????????????????????????????????????????????????????????????????????
        entity.setZcxscz(shEntity.getUploader());
        entity.setSfyycqzcx(1);
        entity.setWjm(shEntity.getWjm());
        entity.setYdm(shEntity.getYdm());
        entity.setZjm(byteObj);

        xxxDao.updateById(entity);
//        if (entity.getSfyycqzcx() == 0){
//            //?????????xxx???????????????????????????????????????
//            entity.setSfyycqzcx(1);
//            entity.setWjm(shEntity.getWjm());
//            entity.setYdm(shEntity.getYdm());
//            entity.setZjm(byteObj);
//            xxxDao.updateById(entity);
//        }else {
//            //todo ?????????????????????????????????????????????????????????????????????
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

    //    // ??????xxx_id??????cqzcx
//    @Transactional
//    @Override
//    public boolean addCqzcx(Long xxx_id, MultipartFile file) throws IOException {
//2
//
//        // ??????JavaCode
//        String JavaCode = FileUtils.getContentByFile(file);
//        // ??????JavaCode
//        Map<String, byte[]> bytecode = DynamicLoader.compile("Extract.java", JavaCode);
//        // ???????????????
//        byte[] classbyte = bytecode.get("Extract");
//        Byte[] class_byte = ArrayUtils.toObject(classbyte);
//
//        // ??????xxx
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
//        // ??????JavaCode
//        String JavaCode = FileUtils.getContentByFile(file);
//        // ??????JavaCode
//        Map<String, byte[]> bytecode = DynamicLoader.compile("Extract.java", JavaCode);
//        // ???????????????
//        byte[] classbyte = bytecode.get("Extract");
//        Byte[] class_byte = ArrayUtils.toObject(classbyte);
//
//        // ??????xxxEntity
//        XxxEntity xxxEntity = xxxDao.selectById(xxx_id);
//        // ??????xxxEntity
//        xxxEntity.setYdm(JavaCode);
//        xxxEntity.setZjm(class_byte);
//        // ??????cqzcxEntity
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

//    // ???????????????
//    @Override
//    @Transactional
//    public boolean removeByIds(Long[] ids) {
//
////        for (Long id : ids) {
////
////            // ??????xxx_id??????ayXxxEntityList
////            QueryWrapper<AyXxxEntity> queryWrapper_AyXxxEntity = new QueryWrapper<>();
////            queryWrapper_AyXxxEntity.eq("xxx_id", id);
////            List<AyXxxEntity> ayXxxEntityList = ayXxxDao.selectList(queryWrapper_AyXxxEntity);
////
////            // ??????ayXxxEntityList
////            for (AyXxxEntity ayXxxEntity : ayXxxEntityList) {
////                // ???????????????ay???
////                QueryWrapper<AyEntity> queryWrapper_AyEntity = new QueryWrapper<>();
////                queryWrapper_AyEntity.eq("id", ayXxxEntity.getAyId());
////                AyEntity ayEntity = ayDao.selectOne(queryWrapper_AyEntity);
////                ayEntity.setYyxxxgs(ayEntity.getYyxxxgs() - 1);
////                ayDao.updateById(ayEntity);
////            }
////
////            // ??????ay_xxx???
////            UpdateWrapper<AyXxxEntity> updateWrapper_AyXxxEntity = new UpdateWrapper<>();
////            updateWrapper_AyXxxEntity.eq("xxx_id", id);
////            ayXxxDao.delete(updateWrapper_AyXxxEntity);
////        }
////
////        // ??????xxx
////        xxxDao.deleteBatchIds(Arrays.asList(ids));
//
//        return true;
//    }

//    // ??????xxx_id??????AyEntityList
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