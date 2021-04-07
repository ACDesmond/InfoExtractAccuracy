package com.nju.infoextract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nju.infoextract.dao.ShDao;
import com.nju.infoextract.entity.ShEntity;
import com.nju.infoextract.entity.XxxEntity;
import com.nju.infoextract.service.ShService;
import com.nju.infoextract.utils.FileUtils;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author: songqiang
 * @since: 2020/11/30
 */
@Service("shService")
public class ShServiceImpl extends ServiceImpl<ShDao, ShEntity> implements ShService {
    @Resource
    private ShDao shDao;

    @Override
    public PageUtils queryShPage(Map<String, Object> params) {
        QueryWrapper<ShEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "newxxx", "aytype","xxxmc", "xxxname", "xxxsx","uploader", "wjm", "ydm", "status", "comment").eq("status", 0);
        IPage<ShEntity> page = this.page(
                new Query<ShEntity>().getPage(params),
                queryWrapper
                //new QueryWrapper<ShEntity>().select("id", "newxxx", "xxxmc", "xxxname", "xxxsx","uploader", "wjm", "ydm", "state", "comment")
        );

        return new PageUtils(page);
    }

    @Override
    public boolean addShZcx(MultipartFile file, String ayType, String xxx, String name, String sx, String uploader) throws IOException {
        String wjm = file.getOriginalFilename();
        String javaCode = FileUtils.getContentByFile(file);
        ShEntity shEntity = new ShEntity();
        shEntity.setNewxxx(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(sx) ? 1 : 0);
        shEntity.setAytype(ayType);
        shEntity.setXxxmc(xxx);
        shEntity.setXxxname(name);
        shEntity.setXxxsx(sx);
        shEntity.setUploader(uploader);
        shEntity.setWjm(wjm);
        shEntity.setYdm(javaCode);
        shEntity.setStatus(0);
        shEntity.setComment("");

        shDao.insert(shEntity);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ShEntity getShItem(Long id, int status, String comment) {
        //先更新审核结果
        updateShItem(id, status, comment);
        //再返回审核后的对象
        ShEntity res = shDao.selectOne(new QueryWrapper<ShEntity>().
                select("id","newxxx","aytype","xxxmc", "xxxname", "xxxsx", "uploader", "wjm","ydm").eq("id", id));
        return res;
    }

    @Override
    public void updateShItem(Long id, int status, String comment) {
        ShEntity updateEntity = new ShEntity();
        updateEntity.setId(id);
        updateEntity.setStatus(status);
        updateEntity.setComment(comment);
        shDao.updateById(updateEntity);
    }
}
