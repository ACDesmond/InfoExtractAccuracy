package com.nju.infoextract.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nju.infoextract.entity.AyXxxEntity;
import com.nju.infoextract.service.AyXxxService;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.utils.R;



/**
 * 案由_信息项关系表
 *
 * @author piaorongzhen
 * @email piaorongzhen@qq.com
 * @date 2020-04-20 17:02:36
 */
@RestController
@RequestMapping("infoextract/ayxxx")
public class AyXxxController {
    @Autowired
    private AyXxxService ayXxxService;

    /**
     * 列表
     */
    @RequestMapping("/list")

    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ayXxxService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{ayId}")
    public R info(@PathVariable("ayId") Long ayId){
		AyXxxEntity ayXxx = ayXxxService.getById(ayId);

        return R.ok().put("ayXxx", ayXxx);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AyXxxEntity ayXxx){
		ayXxxService.save(ayXxx);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AyXxxEntity ayXxx){
		ayXxxService.updateById(ayXxx);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ayIds){
		ayXxxService.removeByIds(Arrays.asList(ayIds));

        return R.ok();
    }

}
