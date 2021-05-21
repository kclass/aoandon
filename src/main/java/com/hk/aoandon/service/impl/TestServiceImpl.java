package com.hk.aoandon.service.impl;

import com.hk.aoandon.mapper.TestMapper;
import com.hk.aoandon.service.TestService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author kai.hu
 * @date 2020/10/20 15:21
 */
@Service
public class TestServiceImpl implements TestService {
    private TestMapper testMapper;

    public TestServiceImpl(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @Override
    public Integer selectList() {
        return testMapper.selectList();
    }

    @Cacheable("test")
    @Override
    public String exportTest(String val) {
        System.out.println("skadgldjslgkjdsalg;");
        return val;
    }

    @Override
    public String exportTest2(String val) {
        return exportTest(val);
    }
}
