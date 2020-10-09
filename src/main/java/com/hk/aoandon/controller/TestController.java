package com.hk.aoandon.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kai.hu
 * @date 2020/10/9 16:58
 */
@RestController
@RequestMapping("test")
public class TestController {

    @ApiOperation("test")
    @GetMapping("test")
    public String test() {
        return "asdgsadg";
    }
}
