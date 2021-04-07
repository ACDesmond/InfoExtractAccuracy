//package com.nju.infoextract.controller;
//
//import java.util.List;
//import java.util.Map;
//
//
//import com.nju.infoextract.entity.AyEntity;
//import com.nju.infoextract.entity.XxxEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import com.nju.infoextract.service.AyService;
//import com.nju.infoextract.utils.PageUtils;
//import com.nju.infoextract.utils.R;
//
//
//
//
///**
// * 案由表
// *
// * @author piaorongzhen
// * @email piaorongzhen@qq.com
// * @date 2020-04-20 17:02:36
// */
//@RestController
//@RequestMapping("infoextract/ay")
//public class AyTmpController {
//    @Autowired
//    private AyService ayService;
//
//    /**
//     * 查询案由拥有信息项
//     */
//    @CrossOrigin(origins = {"*"})
//    @GetMapping("/xxxList")
//    public R ayList(@RequestParam Map<String, Object> params){
//        String ay_id = (String) params.get("ay_id");
//        Long id = Long.parseLong(ay_id);
//
//        List<XxxEntity> xxxEntityList = ayService.getXxxEntityListById(id);
//
//        return R.ok().put("list", xxxEntityList);
//    }
//    /**
//     * 更新案由拥有信息项
//     */
//    @CrossOrigin(origins = {"*"})
//    @PostMapping("/xxxList")
//    public R updateAyList(@RequestParam("id") Long id, @RequestBody List<XxxEntity> xxxEntityList){
//
//        ayService.updateAyXxx(id, xxxEntityList);
//
//        return R.ok();
//    }
//
//    /**
//     * 列表
//     */
//    @CrossOrigin(origins = {"*"})
//    @RequestMapping("/list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = ayService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }
//
//
//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{id}")
//    public R info(@PathVariable("id") Long id){
//		AyEntity ay = ayService.getById(id);
//
//        return R.ok().put("ay", ay);
//    }
//
//    /**
//     * 保存
//     */
//    @CrossOrigin(origins = {"*"})
//    @RequestMapping("/save")
//    public R save(@RequestBody AyEntity ay){
//		ayService.save(ay);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @CrossOrigin(origins = {"*"})
//    @RequestMapping("/update")
//    public R update(@RequestBody AyEntity ay){
//		ayService.updateById(ay);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @CrossOrigin(origins = {"*"})
//    @RequestMapping("/delete")
//    public R delete(@RequestBody Long[] ids){
//		ayService.removeByIds(ids);
//
//        return R.ok();
//    }
//
//}
