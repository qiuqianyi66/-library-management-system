-- ============================================================
-- 高校图书管理系统 - 模拟数据填充脚本
-- 为仪表盘、图表、推荐系统提供真实数据
-- ============================================================

-- 清空旧数据（保留原有图书和读者）
DELETE FROM tb_book_review;
DELETE FROM tb_fine_record;
DELETE FROM tb_payment_order;
DELETE FROM tb_borrow_record;
ALTER TABLE tb_borrow_record AUTO_INCREMENT = 1;
ALTER TABLE tb_fine_record AUTO_INCREMENT = 1;
ALTER TABLE tb_payment_order AUTO_INCREMENT = 1;
ALTER TABLE tb_book_review AUTO_INCREMENT = 1;

-- 重置库存为初始值
UPDATE tb_book SET stock_count = 5 WHERE book_id = 1;
UPDATE tb_book SET stock_count = 3 WHERE book_id = 2;
UPDATE tb_book SET stock_count = 8 WHERE book_id = 3;
UPDATE tb_book SET stock_count = 6 WHERE book_id = 4;
UPDATE tb_book SET stock_count = 4 WHERE book_id = 5;
UPDATE tb_book SET stock_count = 5 WHERE book_id = 6;

-- ============================================================
-- 1. 借阅记录（近30天，让趋势图有数据可显示）
-- ============================================================

-- reader 1 (李丽) - 借了3本，其中1本逾期
INSERT INTO tb_borrow_record (reader_id, book_id, borrow_date, due_date, return_date, status, renew_count, fine_amount, create_by, create_time)
VALUES
(1, 1, DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), NULL, 'overdue', 1, 2.50, 'admin', DATE_SUB(NOW(), INTERVAL 25 DAY)),
(1, 3, DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY), 'returned', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 15 DAY)),
(1, 5, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 27 DAY), NULL, 'borrowing', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY));

-- reader 2 (赵健) - 借了4本，已还2本
INSERT INTO tb_borrow_record (reader_id, book_id, borrow_date, due_date, return_date, status, renew_count, fine_amount, create_by, create_time)
VALUES
(2, 2, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 'returned', 1, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 20 DAY)),
(2, 4, DATE_SUB(NOW(), INTERVAL 18 DAY), DATE_ADD(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 'returned', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 18 DAY)),
(2, 6, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_ADD(NOW(), INTERVAL 20 DAY), NULL, 'borrowing', 1, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 10 DAY)),
(2, 1, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_ADD(NOW(), INTERVAL 25 DAY), NULL, 'borrowing', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 5 DAY));

-- reader 3 (李建林) - 借了2本，已还
INSERT INTO tb_borrow_record (reader_id, book_id, borrow_date, due_date, return_date, status, renew_count, fine_amount, create_by, create_time)
VALUES
(3, 3, DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_ADD(NOW(), INTERVAL 18 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 'returned', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 12 DAY)),
(3, 5, DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_ADD(NOW(), INTERVAL 22 DAY), NULL, 'borrowing', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 8 DAY));

-- reader 4 (张飞) - 借了1本，逾期
INSERT INTO tb_borrow_record (reader_id, book_id, borrow_date, due_date, return_date, status, renew_count, fine_amount, create_by, create_time)
VALUES
(4, 2, DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), NULL, 'overdue', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 30 DAY));

-- ============================================================
-- 2. 更新库存（根据借阅记录调整）
-- ============================================================
UPDATE tb_book SET stock_count = stock_count - 2 WHERE book_id = 1; -- 2本在借
UPDATE tb_book SET stock_count = stock_count - 1 WHERE book_id = 2; -- 1本在借 + 1本逾期
UPDATE tb_book SET stock_count = stock_count - 1 WHERE book_id = 3; -- 1本在借（其他已还）
UPDATE tb_book SET stock_count = stock_count - 2 WHERE book_id = 4; -- 0本在借（已还）
UPDATE tb_book SET stock_count = stock_count - 1 WHERE book_id = 5; -- 1本在借
UPDATE tb_book SET stock_count = stock_count - 1 WHERE book_id = 6; -- 1本在借

-- ============================================================
-- 3. 罚款记录（逾期产生的）
-- ============================================================
-- 李丽逾期5天，每天0.5元 = 2.5元
INSERT INTO tb_fine_record (borrow_id, reader_id, amount, status, pay_method, pay_time, create_by, create_time)
VALUES (1, 1, 2.50, '0', NULL, NULL, 'admin', DATE_SUB(NOW(), INTERVAL 5 DAY));

-- 张飞逾期，还没算罚款但记录逾期状态

-- ============================================================
-- 4. 图书评论/评分
-- ============================================================
INSERT INTO tb_book_review (book_id, reader_id, rating, content, status, create_by, create_time)
VALUES
(1, 1, 5, '经典的Java入门书籍，内容详实，适合初学者。', '1', 'admin', DATE_SUB(NOW(), INTERVAL 10 DAY)),
(1, 2, 4, '写得很清晰，例子丰富，就是有些章节偏旧了。', '1', 'admin', DATE_SUB(NOW(), INTERVAL 8 DAY)),
(2, 2, 5, '数据结构必备，讲解深入浅出，配合习题效果很好。', '1', 'admin', DATE_SUB(NOW(), INTERVAL 7 DAY)),
(3, 1, 4, '高等数学经典教材，逻辑严密，习题量大。', '1', 'admin', DATE_SUB(NOW(), INTERVAL 6 DAY)),
(3, 3, 3, '内容偏难，需要老师辅导才能完全理解。', '1', 'admin', DATE_SUB(NOW(), INTERVAL 5 DAY)),
(5, 1, 5, 'AI入门必读，覆盖了主要的技术方向。', '1', 'admin', DATE_SUB(NOW(), INTERVAL 4 DAY)),
(6, 2, 4, '数据库理论讲解系统，SQL部分很实用。', '1', 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY)),
(4, 2, 4, '英语教材编排合理，适合大学生使用。', '1', 'admin', DATE_SUB(NOW(), INTERVAL 2 DAY));
