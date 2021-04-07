package com.nju.infoextract.controller;

import com.nju.infoextract.service.AyInfoService;
import com.nju.infoextract.service.AyService;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: songqiang
 * @since: 2020/12/15
 */
@RestController
@RequestMapping("infoextract/ay")
public class AyController {

    @Resource
    private AyInfoService ayInfoService;

    @Resource
    private AyService ayService;

    /**
     * 获取案由info的list
     */
    @CrossOrigin(origins = {"*"})
    @GetMapping("/info")
    public R getAyInfo(@RequestParam Map<String, Object> params){
        PageUtils page = ayInfoService.queryAyInfoPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 添加案由info
     */
    @CrossOrigin(origins = {"*"})
    @PostMapping("/addinfo")
    public R addAyInfo(@RequestBody Map<String, Object> params) {

        String newAymc = String.valueOf(params.get("newaymc"));
        ayInfoService.addAyInfo(newAymc);
        return R.ok();
    }

    /**
     * 更新案由info
     * @return
     */
    @CrossOrigin(origins = {"*"})
    @PostMapping("/updateinfo")
    public R updateAyInfo(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        String newType = String.valueOf(params.get("type"));
        int yyxxxgs = Integer.parseInt(params.get("yyxxxgs").toString());
        ayInfoService.updateAyInfo(id, newType, yyxxxgs);
        return R.ok();
    }

    @CrossOrigin(origins = {"*"})
    @GetMapping("/ay")
    public R getAyList(@RequestParam Map<String, Object> params) {
        PageUtils page = ayService.queryAyPage(params);
        return R.ok().put("page", page);
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping("/adday")
    public R addAy(@RequestBody Map<String, Object> params) {
        String type = String.valueOf(params.get("type"));
        String title = String.valueOf(params.get("title"));
        String content = String.valueOf(params.get("content"));
        ayService.addAy(type, title, content);

        return R.ok();
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping("/updateay")
    public R updateAy(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        String type = String.valueOf(params.get("type"));
        String title = String.valueOf(params.get("title"));
        String content = String.valueOf(params.get("content"));
        int sfbz  = Integer.parseInt(params.get("sfbz").toString());
        String bzjg = String.valueOf(params.get("bzjg"));
        ayService.updateAy(id, type, title, content, sfbz, bzjg);
        return R.ok();
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping("/deleteay")
    public R deleteAy(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        ayService.deleteAy(id);
        return R.ok();
    }
}
