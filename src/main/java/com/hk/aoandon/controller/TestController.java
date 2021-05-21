package com.hk.aoandon.controller;

import com.hk.aoandon.service.TestService;
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
   private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("exportTest")
    public String exportTest(String val) {

        return testService.exportTest(val);
    }
    @GetMapping("exportTest2")
    public String exportTest2(String val) {

        return testService.exportTest2(val);
    }

}
