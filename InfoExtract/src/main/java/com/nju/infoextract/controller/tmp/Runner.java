package com.nju.infoextract.controller.tmp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author: songqiang
 * @since: 2020/11/20
 */
@Component
public class Runner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("开始初始化UserDb");

        String[] editorRole = {"editor"};
        String[] adminRole = {"admin"};

        MockUser editor = new MockUser(
                "li",
                "111111",
                editorRole,
                "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
        );

        MockUser admin = new MockUser(
                "ming",
                "111111",
                adminRole,
                "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
        );

        MockDb.users.add(editor);
        MockDb.users.add(admin);
        System.out.println("UserDb初始化完成... ...");

    }
}
