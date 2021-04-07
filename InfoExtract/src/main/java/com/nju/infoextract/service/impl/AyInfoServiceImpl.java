package com.nju.infoextract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nju.infoextract.dao.AyInfoDao;
import com.nju.infoextract.entity.AyInfoEntity;
import com.nju.infoextract.service.AyInfoService;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: songqiang
 * @since: 2020/12/15
 */
@Service("ayInfoService")
public class AyInfoServiceImpl extends ServiceImpl<AyInfoDao, AyInfoEntity> implements AyInfoService {
    @Resource
    private AyInfoDao ayInfoDao;

    @Override
    public PageUtils queryAyInfoPage(Map<String, Object> params) {
        QueryWrapper<AyInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","type", "yyxxxgs");
        IPage<AyInfoEntity> page = this.page(
                new Query<AyInfoEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public boolean addAyInfo(String newAymc) {
        AyInfoEntity ayInfoEntity = new AyInfoEntity();
        ayInfoEntity.setType(newAymc);
        ayInfoEntity.setYyxxxgs(0);
        ayInfoDao.insert(ayInfoEntity);
        return true;
    }

    @Override
    public void updateAyInfo(Long id, String type, int yyxxxgs) {
        AyInfoEntity updateEntity = new AyInfoEntity();
        updateEntity.setId(id);
        updateEntity.setType(type);
        updateEntity.setYyxxxgs(yyxxxgs);
        ayInfoDao.updateById(updateEntity);
    }

}
