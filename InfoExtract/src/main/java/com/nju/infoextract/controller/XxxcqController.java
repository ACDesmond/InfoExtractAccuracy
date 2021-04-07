package com.nju.infoextract.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("xxxcq")
public class XxxcqController {

//    @Autowired
//    private XxxcqService xxxcqService;
//
//
//    @CrossOrigin(origins = {"http://localhost:9528", "null"})
//    @PostMapping("/posts")
//    @ResponseBody
//    public String posts(HttpServletRequest request) {
//
//        try {
//
//            String aydm = (String) request.getHeader("aydm");
//            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
//            for (int i = 0; i < files.size(); i++) {
//                MultipartFile file = files.get(i);
//                Document document = xxxcqService.xxxcq(aydm, file);
//                System.out.println(document.toString());
//            }
//            return "ok";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "error";
//    }
}
