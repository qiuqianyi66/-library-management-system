package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.Book;
import com.ruoyi.TestApplication;
import com.ruoyi.system.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestApplication.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("查询图书列表")
    public void testSelectBookList() {
        Book book = new Book();
        List<Book> list = bookService.selectBookList(book);
        assertNotNull(list, "查询图书列表不应返回null");
    }

    @Test
    @DisplayName("校验图书名称唯一性")
    public void testBookNameUnique() {
        Book book = new Book();
        book.setBookName("测试图书名称");
        boolean result = bookService.checkBookNameUnique(book);
        assertNotNull(bookService, "BookService不应为null");
    }
}
