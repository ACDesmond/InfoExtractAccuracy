package com.nju.infoextract.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nju.infoextract.controller.tmp.MockDb;
import com.nju.infoextract.utils.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nju.infoextract.entity.XxxEntity;
import com.nju.infoextract.service.XxxService;
import com.nju.infoextract.utils.PageUtils;
import com.nju.infoextract.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 信息项
 *
 * @author songqiang
 * @date 2020-04-20 17:02:36
 */
@RestController
@RequestMapping("infoextract/xxx")
public class XxxController {
    @Autowired
    private XxxService xxxService;


//    /**
//     * 添加抽取子程序或覆盖已有抽取子程序
//     */
//    @CrossOrigin(origins = {"*"})
//    @RequestMapping("/addCqzcx")
//    public R addCqzcx(@RequestParam("file") MultipartFile file, HttpServletRequest request){
//        try {
//            // 获信息项id
//            String xxx_id_temp = (String) request.getHeader("xxx_id");
//            Long xxx_id = Long.parseLong(xxx_id_temp);
//
//            // 获取是否拥有抽取子程序
//            String sfyycqzcx = (String) request.getHeader("sfyycqzcx");
//
//            if (sfyycqzcx.equals("1")) {
//
//                // 更新cqzcx
//                xxxService.update(xxx_id, file);
//
//            } else {
//                // 添加抽取子程序
//                xxxService.addCqzcx(xxx_id, file);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.error();
//        }
//
//        return R.ok();
//
//    }

//    /**
//     * 信息项所属案由列表
//     */
//    @CrossOrigin(origins = {"*"})
//    @RequestMapping("/ayList")
//    public R ayList(@RequestParam Map<String, Object> params){
//        String xxx_id = (String) params.get("xxx_id");
//        Long id = Long.parseLong(xxx_id);
//        List<AyEntity> ayEntityList = xxxService.getAyEntityListById(id);
//        return R.ok().put("list", ayEntityList);
//    }

    //接受用户名和案由文件，保存到MockDb中；这个应该保存到redis中缓存起来
    @CrossOrigin(origins = {"*"})
    @RequestMapping("/extract")
    public R extract(@RequestParam MultipartFile file,
                     @RequestParam("user") String username) throws IOException {
        //拿到来信息项列表，每个信息项都对应了一个子程序
        //接下来按顺序调用每一个信息项的抽取子程序，把结果组装成一个xml格式的文本
        String content = FileUtils.getContentByFile(file);
        System.out.println("用户" + username + "上传到服务器到文书内容是： " + content );
        xxxService.storeAy(FileUtils.getContentByFile(file), username);
        //todo 构造xml
        return R.ok();
    }

    //点击下载后才会执行真的的抽取
    @CrossOrigin(origins = {"*"})
    @PostMapping("/extracttest")
    public ResponseEntity<FileSystemResource> extract(@RequestBody Map<String, Object> params
//                                                      @RequestParam("xx") String[] list

                                                      ) throws IOException {
        //用户名
        String user = (String) params.get("user");
        System.out.println("userissss: " + user);
        //信息项列表
        ArrayList<String> list = (ArrayList<String>) params.get("Xxx");

        for(String str: list){
            System.out.println(str);
        }

        Document document = xxxService.extract(MockDb.aystore.get(user), user, list);

//        Document doc = DocumentHelper.createDocument();
//        Element root = doc.addElement("list");
//        root.addElement("dept").addText("就是一个测试");
//        root.addAttribute("id", "1");
        File file = new File("res.xml");
        XMLWriter writer = new XMLWriter(new FileOutputStream(file),
                OutputFormat.createPrettyPrint());
        writer.setEscapeText(false);
        writer.write(document);
        System.out.println("tmp: " + file.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Access-Control-Allow-Origin","*");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok().headers(headers).
                contentLength(file.length()).
                contentType(MediaType.parseMediaType("application/octet-stream")).
                body(new FileSystemResource(file));

        //return R.ok();
    }


    @CrossOrigin(origins = {"*"})
    @RequestMapping("/list")
    public R xxxList(@RequestParam Map<String, Object> params){
        System.out.println("xxx/list...");
        System.out.println(params.size());
        PageUtils page = xxxService.queryXxxPage(params);

        return R.ok().put("page", page);
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping("/addxxx")
    public R addXxx(@RequestBody Map<String, Object> params) {
        String xxxmc = String.valueOf(params.get("xxxmc"));
        String xxxsxjc = String.valueOf(params.get("xxxsxjc"));
        String aytype = String.valueOf(params.get("aytype"));
        xxxService.addXxx(xxxmc, xxxsxjc, aytype);
        return R.ok();
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping("/updatexxx")
    public R updataXxx(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        String xxxmc = String.valueOf(params.get("xxxmc"));
        String xxxsxjc = String.valueOf(params.get("xxxsxjc"));
        int sfyycqzcx = Integer.parseInt(params.get("sfyycqzcx").toString());
        String aytype = String.valueOf(params.get("aytype"));
        String zcxscz = String.valueOf(params.get("zcxscz"));
        String wjm = String.valueOf(params.get("wjm"));
        String ydm = String.valueOf(params.get("ydm"));
        xxxService.updateXxx(id, xxxmc, xxxsxjc, sfyycqzcx, aytype, zcxscz, wjm, ydm);
        return R.ok();
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping("/deletexxx")
    public R deleteXxx(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        xxxService.deleteXxx(id);
        return R.ok();
    }






//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{id}")
//    public R info(@PathVariable("id") Long id){
//		XxxEntity xxx = xxxService.getById(id);
//
//        return R.ok().put("xxx", xxx);
//    }

//    /**
//     * 保存
//     */
//    @CrossOrigin(origins = {"*"})
//    @RequestMapping("/save")
//    public R save(@RequestBody XxxEntity xxx){
//		xxxService.save(xxx);
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @CrossOrigin(origins = {"*"})
//    @RequestMapping("/update")
//    public R update(@RequestBody XxxEntity xxx){
//
//		xxxService.updateById(xxx);
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @CrossOrigin(origins = {"*"})
//    @RequestMapping("/delete")
//    public R delete(@RequestBody Long[] ids){
//
//		xxxService.removeByIds(ids);
//
//        return R.ok();
//    }

}
