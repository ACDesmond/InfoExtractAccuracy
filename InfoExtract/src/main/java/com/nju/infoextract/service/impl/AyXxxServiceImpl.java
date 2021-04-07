package com.nju.infoextract.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.utils.Query;

import com.nju.infoextract.dao.AyXxxDao;
import com.nju.infoextract.entity.AyXxxEntity;
import com.nju.infoextract.service.AyXxxService;


@Service("ayXxxService")
public class AyXxxServiceImpl extends ServiceImpl<AyXxxDao, AyXxxEntity> implements AyXxxService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AyXxxEntity> page = this.page(
                new Query<AyXxxEntity>().getPage(params),
                new QueryWrapper<AyXxxEntity>()
        );

        return new PageUtils(page);
    }

}