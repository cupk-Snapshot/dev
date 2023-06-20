package com.cupk.snapshot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Guo Tianyou on 2023/6/17.
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";

    }

}
