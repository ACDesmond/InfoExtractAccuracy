package com.nju.infoextract.controller;

import com.nju.infoextract.common.compiler.CodeHelper;
import com.nju.infoextract.entity.ShEntity;
import com.nju.infoextract.service.XxxService;
import com.nju.infoextract.service.impl.ShServiceImpl;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.utils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author: songqiang
 * @since: 2020/11/30
 */
@RestController
@RequestMapping("infoextract/sh")
public class ShController {

    @Resource
    private ShServiceImpl shService;

    @Resource
    private XxxService xxxService;

    /**
     * <p>
     *     上传子程序，数据添加到子程序审核表
     * </p>
     *
     * @param file 子程序文件
     * @param xxx 所选的信息项
     * @param name 新创建并提交的信息项
     * @param sx 新创建并提交的信息项缩写
     * @return
     */
    @CrossOrigin(origins = {"*"})
    @PostMapping("/addshzcx")
    public R addShzcx(@RequestParam MultipartFile file,
                      @RequestParam("aytype") String ayType,
                      @RequestParam("xxx") String xxx,
                      @RequestParam("name") String name,
                      @RequestParam("sx") String sx,
                      @RequestParam("uploader") String uploader) throws IOException {
        //todo 假设传到这里的参数都是按照正确的格式传的
        //如果name和sx不为空，说明editor需要创建新的信息项
        shService.addShZcx(file,ayType,  xxx, name, sx, uploader);
        return R.ok();
    }

    /**
     * <p>
     *     返回待管理员审核的信息项
     * </p>
     *
     * @param params
     * @return
     */
    @CrossOrigin(origins = {"*"})
    @GetMapping("/shlist")
    public R shXxxList(@RequestParam Map<String, Object> params) {
        System.out.println("shxxx/list...");
        PageUtils page = shService.queryShPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 管理员提交信息项的审核结果
     *
     * @param params
     * @return
     */
    @CrossOrigin(origins = {"*"})
    @PostMapping("/submit")
    public R submitCheck(@RequestBody Map<String, Object> params) {
        System.out.println("sh-submitcheck");
        //审核表中的id
        Long id = Long.parseLong(params.get("id").toString());
        //审核后的状态
        int status = Integer.parseInt(params.get("status").toString());
        //不需要传输子程序的提交者，直接从审核表里读出来

        //审核时由管理员添加的备注
        String comment = String.valueOf(params.get("comment"));

        if (status == 1){
            //对于审核通过的
            //编译源代码
            ShEntity item = shService.getShItem(id, status, comment);
            Byte[] byteObj = CodeHelper.getByteCode(item.getWjm(), item.getYdm());

            if (item.getNewxxx() == 1){
                //如果信息项是新建的，那么就写一条新的记录到xxx表
                xxxService.addXxxWithZcx(item, byteObj);
            }else {
                //如果信息项不是新建的，则看它原来有无抽取子程序
                //如果没有，直接更新信息项表
                //todo 如果有,暂时采取直接覆盖的策略
                xxxService.addZcx(item, byteObj);
            }
        }else {
            //对于审核不通过的
            shService.updateShItem(id, status, comment);
        }
        return R.ok();
    }
}
