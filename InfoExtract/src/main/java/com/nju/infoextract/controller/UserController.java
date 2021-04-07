package com.nju.infoextract.controller;

import com.alibaba.fastjson.JSON;
import com.nju.infoextract.controller.tmp.MockDb;
import com.nju.infoextract.controller.tmp.MockUser;
import com.nju.infoextract.controller.tmp.Token;
import com.nju.infoextract.controller.tmp.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: songqiang
 * @since: 2020/11/20
 */
@RestController
@RequestMapping("infoextract/user")
public class UserController {

    @PostMapping("/login")
    public String userLogin(@RequestBody HashMap<String, String> map) {
        System.out.println("用户名是" + map.get("username"));
        System.out.println("mima是" + map.get("password"));

        String token = UUID.randomUUID().toString();
        //todo 后端用户鉴定逻辑，先使用mock数据

        String username = map.get("username");

        String[] role = {};
        String avatar = "";

        for (MockUser mockUser : MockDb.users){
            if (mockUser.getUsername().equals(username)){
                role = mockUser.getRoles();
                avatar = mockUser.getAvatar();
                break;
            }
        }

        User user = new User(username, role, avatar);
        String jsonUser = JSON.toJSONString(user);

        //存储token
        MockDb.tokens.put(token, jsonUser);

        Map<String, Object> res = new HashMap<>();
        res.put("code", 20000);
        res.put("data", new Token(token));
        return JSON.toJSONString(res);
    }

    @GetMapping("/info")
    public String getUserInfo(HttpServletRequest request) {
        //todo mock数据
        Map<String, Object> res = new HashMap<>();
        String token = request.getAttribute("token").toString();
        String userStr = MockDb.tokens.get(token);
        User user = JSON.parseObject(userStr, User.class);
        res.put("code", 20000);
        res.put("data", user);
        return JSON.toJSONString(res);
    }

    @PostMapping("/logout")
    public String userLogout(HttpServletRequest request) {
        //todo mock数据
        Map<String, Object> res = new HashMap<>();
        String token = request.getAttribute("token").toString();
        MockDb.tokens.remove(token);
        res.put("code", 20000);
        return JSON.toJSONString(res);
    }
}
