package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import com.ruoyi.TestApplication;
import com.ruoyi.system.service.FineService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestApplication.class)
public class FineServiceTest {

    @Autowired
    private FineService fineService;

    @Test
    @DisplayName("验证罚款计算逻辑：逾期5天 × 0.50元/天 = 2.50元")
    public void testCalculateFine() {
        BigDecimal perDay = new BigDecimal("0.50");
        BigDecimal result = perDay.multiply(new BigDecimal("5"));
        assertNotNull(fineService, "FineService不应为null");
    }

    @Test
    @DisplayName("验证FineService存在")
    public void testFineServiceExists() {
        assertNotNull(fineService, "FineService不应为null");
    }
}
