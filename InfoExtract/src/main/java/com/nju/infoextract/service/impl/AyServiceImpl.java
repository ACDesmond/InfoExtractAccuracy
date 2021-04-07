package com.nju.infoextract.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.nju.infoextract.dao.AyXxxDao;
import com.nju.infoextract.dao.XxxDao;
import com.nju.infoextract.entity.AyEntity;
import com.nju.infoextract.entity.AyXxxEntity;
import com.nju.infoextract.entity.XxxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.utils.Query;

import com.nju.infoextract.dao.AyDao;
import com.nju.infoextract.service.AyService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("ayService")
public class AyServiceImpl extends ServiceImpl<AyDao, AyEntity> implements AyService {

    @Resource
    private AyDao ayDao;

    @Autowired
    private AyXxxDao ayXxxDao;
    @Autowired
    private XxxDao xxxDao;

    @Override
    public PageUtils queryAyPage(Map<String, Object> params) {
        IPage<AyEntity> page = this.page(
                new Query<AyEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public void addAy(String type, String title, String content) {
        AyEntity ayEntity = new AyEntity();
        ayEntity.setType(type);
        ayEntity.setTitle(title);
        ayEntity.setContent(content);
        ayEntity.setSfbz(0);
        ayEntity.setBzjg("");
        ayDao.insert(ayEntity);
    }

    @Override
    public void updateAy(Long id, String type, String title, String content, int sfbz, String bzjg){
        AyEntity updateEntity = new AyEntity();
        updateEntity.setId(id);
        updateEntity.setType(type);
        updateEntity.setTitle(title);
        updateEntity.setContent(content);
        updateEntity.setSfbz(sfbz);
        updateEntity.setBzjg(bzjg);
        ayDao.updateById(updateEntity);
    }

    @Override
    public void deleteAy(Long id) {
        ayDao.deleteById(id);
    }




    @Transactional
    @Override
    public boolean removeByIds(Long[] ids) {

        for (Long id : ids) {

            QueryWrapper<AyXxxEntity> queryWrapper_AyXxxEntity = new QueryWrapper<>();
            queryWrapper_AyXxxEntity.eq("ay_id", id);
            List<AyXxxEntity> ayXxxEntityList = ayXxxDao.selectList(queryWrapper_AyXxxEntity);

            for (AyXxxEntity ayXxxEntity : ayXxxEntityList) {
                XxxEntity xxxEntity = xxxDao.selectById(ayXxxEntity.getXxxId());
                xxxEntity.setSsaygs(xxxEntity.getSfyycqzcx() - 1);
                xxxDao.updateById(xxxEntity);
            }
            // 更新ay_xxx表
            UpdateWrapper<AyXxxEntity> updateWrapper_AyXxxEntity = new UpdateWrapper<>();
            updateWrapper_AyXxxEntity.eq("ay_id", id);
            ayXxxDao.delete(updateWrapper_AyXxxEntity);

        }

        // 批量删除ay
        ayDao.deleteBatchIds(Arrays.asList(ids));

        return true;
    }

    @Override
    @Transactional
    public List<XxxEntity> getXxxEntityListById(Long id) {

        QueryWrapper<AyXxxEntity> queryWrapper_AyXxxEntity = new QueryWrapper<>();
        queryWrapper_AyXxxEntity.select("xxx_id").eq("ay_id", id);
        List<AyXxxEntity> ayXxxEntityList = ayXxxDao.selectList(queryWrapper_AyXxxEntity);

        List<XxxEntity> xxxEntityList = new ArrayList<>(ayXxxEntityList.size());
        for (AyXxxEntity ayXxxEntity : ayXxxEntityList) {
            QueryWrapper<XxxEntity> queryWrapper_XxxEntity = new QueryWrapper<>();
            queryWrapper_XxxEntity.select("id", "xxxmc", "xxxsxjc").eq("id", ayXxxEntity.getXxxId());
            XxxEntity xxxEntity = xxxDao.selectOne(queryWrapper_XxxEntity);
            xxxEntityList.add(xxxEntity);
        }
        return xxxEntityList;
    }

    @Override
    @Transactional
    public boolean updateAyXxx(Long id, List<XxxEntity> xxxEntityList) {

//        // 更新xxx
//        List<AyXxxEntity> ayXxxEntityList = ayXxxDao.selectList(new QueryWrapper<AyXxxEntity>().eq("ay_id", id));
//        for (AyXxxEntity ayXxxEntity : ayXxxEntityList) {
//            XxxEntity xxxEntity = xxxDao.selectById(ayXxxEntity.getXxxId());
//            xxxEntity.setSsaygs(xxxEntity.getSfyycqzcx() - 1);
//            xxxDao.updateById(xxxEntity);
//        }
//        // 删除旧AyXxxEntity
//        ayXxxDao.delete(new UpdateWrapper<AyXxxEntity>().eq("ay_id", id));
//
//        // 插入新AyXxxEntity
//        for (int i = 1; i <= xxxEntityList.size(); i++) {
//            AyXxxEntity ayXxxEntity = new AyXxxEntity();
//            ayXxxEntity.setAyId(id);
//            ayXxxEntity.setXxxId(xxxEntityList.get(i).getId());
//            ayXxxEntity.setSx(i);
//            ayXxxDao.insert(ayXxxEntity);
//        }
//        // 更新ay
//        AyEntity ayEntity = ayDao.selectById(id);
//        ayEntity.setYyxxxgs(xxxEntityList.size());
//        ayDao.updateById(ayEntity);

        return true;
    }

}