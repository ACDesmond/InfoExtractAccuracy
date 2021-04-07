package com.nju.infoextract.controller;

import com.nju.infoextract.service.AyConfService;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author: songqiang
 * @since: 2021/1/5
 */
@RestController
@RequestMapping("infoextract/ayconf")
public class AyConfController {
    @Resource
    private AyConfService ayConfService;

    @CrossOrigin(origins = {"*"})
    @GetMapping("/list")
    public R getAyConf(@RequestParam(value = "user") String username) {
        PageUtils page = ayConfService.queryAyConfPage(username);
        return R.ok().put("page", page);
    }
    @CrossOrigin(origins = {"*"})
    @PostMapping("/add")
    public R addAyConf(@RequestBody Map<String, Object> params) {
        String user = String.valueOf(params.get("user"));
        String name = String.valueOf(params.get("name"));
        ArrayList<String> xxxList = (ArrayList<String>) params.get("xxx");
        String xxx = String.join(",", xxxList);
        ayConfService.addAyConf(user, name, xxx);
        return R.ok();
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping("/edit")
    public R editAyConf(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        String name = String.valueOf(params.get("name"));
        ArrayList<String> xxxList = (ArrayList<String>) params.get("xxx");
        String xxx = String.join(",", xxxList);
        ayConfService.editAyConf(id, name, xxx);
        return R.ok();
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping("/delete")
    public R deleteAyConf(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        ayConfService.deleteAyConf(id);
        return R.ok();
    }
}
