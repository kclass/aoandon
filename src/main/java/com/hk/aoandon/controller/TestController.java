package com.hk.aoandon.controller;

import com.alibaba.druid.sql.visitor.functions.Bin;
import com.hk.aoandon.service.TestService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @ApiOperation("test")
    @GetMapping("test")
    public Integer test() {
        int i = 10 / 0;
        return testService.selectList();
    }

    @GetMapping("authorize")
    public void authorize(@Valid AuthorizeIn authorize) {
    }

    @Data
    private static class AuthorizeIn{
//        @NotBlank(message = "缺少response_type参数")
//        private String responseType;
//
//        @NotBlank(message = "缺少client_id参数")
//        private String clientId;

        @NotNull(message = "state不能为空")
        @Min(0)
        @Max(100)
        private Integer state;

//        @NotBlank(message = "缺少redirect_uri参数")
//        private String redirectUri;
    }

    @ExceptionHandler
    public String doError(Exception e) {
        StringBuilder errorMsg = new StringBuilder(Strings.EMPTY);
        if (e instanceof BindException) {
            BindingResult result = ((BindException) e).getBindingResult();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMsg.append(error.getDefaultMessage()).append(";");
            }
            return errorMsg.toString();
        }

        return e.getMessage();
    }
}
