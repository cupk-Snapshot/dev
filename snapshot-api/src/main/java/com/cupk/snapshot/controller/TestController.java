package com.cupk.snapshot.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/1")
    @PreAuthorize("hasAuthority('insert')")
    public String test1() {

        return "insert插入操作";
    }

    @GetMapping("/test/2")
    @PreAuthorize("hasAuthority('delete')")
    public String test2() {

        return "delete删除操作";
    }

    @GetMapping("/test/3")
    @PreAuthorize("hasAuthority('update')")
    public String test3() {

        return "update修改操作";
    }

    @GetMapping("/test/4")
    @PreAuthorize("hasAuthority('select')")
    public String test4() {

        return "select查询操作";
    }

    @GetMapping("/test/5")
    @PreAuthorize("permitAll()")
    public String test5() {
        System.out.println(1 / 0);
        return "异常处理";
    }

}
