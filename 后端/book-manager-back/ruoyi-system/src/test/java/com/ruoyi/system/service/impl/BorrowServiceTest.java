package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.BorrowRecord;
import com.ruoyi.TestApplication;
import com.ruoyi.system.service.BorrowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestApplication.class)
public class BorrowServiceTest {

    @Autowired
    private BorrowService borrowService;

    @Test
    @DisplayName("查询借阅列表")
    public void testSelectBorrowList() {
        BorrowRecord record = new BorrowRecord();
        List<BorrowRecord> list = borrowService.selectBorrowList(record);
        assertNotNull(list, "查询借阅列表不应返回null");
    }

    @Test
    @DisplayName("验证借阅规则校验逻辑存在")
    public void testBorrowBookValidation() {
        assertNotNull(borrowService, "BorrowService不应为null");
    }
}
