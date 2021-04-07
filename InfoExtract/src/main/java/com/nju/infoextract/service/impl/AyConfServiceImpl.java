package com.nju.infoextract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nju.infoextract.dao.AyConfDao;
import com.nju.infoextract.entity.AyConfEntity;
import com.nju.infoextract.service.AyConfService;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: songqiang
 * @since: 2021/1/5
 */
@Service("ayConfService")
public class AyConfServiceImpl extends ServiceImpl<AyConfDao, AyConfEntity> implements AyConfService {

    @Resource
    private AyConfDao ayConfDao;

    @Override
    public PageUtils queryAyConfPage(String username) {
        //找自己发布的和管理员发布的推荐配置
//        String username = String.valueOf(params.get("user"));
        System.out.println("username is : " + username);
        QueryWrapper<AyConfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "user","name", "xxx").
                eq("user", username).
                or().
                eq("user", "admin")

        ;
        IPage<AyConfEntity> page = this.page(
                new Query<AyConfEntity>().getPage(new HashMap<>()),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void addAyConf(String user, String name, String xxx) {
        AyConfEntity entity = new AyConfEntity();
        entity.setUser(user);
        entity.setName(name);
        entity.setXxx(xxx);
        ayConfDao.insert(entity);
    }

    @Override
    public void editAyConf(Long id, String name, String xxx) {
        AyConfEntity entity = new AyConfEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setXxx(xxx);
        ayConfDao.updateById(entity);
    }

    @Override
    public void deleteAyConf(Long id) {
        ayConfDao.deleteById(id);
    }
}
