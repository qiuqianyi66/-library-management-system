-- 补充近7天的借阅数据（让趋势图更丰富）
INSERT INTO tb_borrow_record (reader_id, book_id, borrow_date, due_date, return_date, status, renew_count, fine_amount, create_by, create_time)
VALUES
(1, 4, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_ADD(NOW(), INTERVAL 23 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 'returned', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 7 DAY)),
(2, 5, DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 'returned', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 6 DAY)),
(3, 1, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_ADD(NOW(), INTERVAL 25 DAY), NULL, 'borrowing', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 5 DAY)),
(4, 6, DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_ADD(NOW(), INTERVAL 26 DAY), NULL, 'borrowing', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 4 DAY)),
(1, 2, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 27 DAY), NULL, 'borrowing', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 3 DAY)),
(2, 3, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 28 DAY), NULL, 'borrowing', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 2 DAY)),
(3, 4, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 29 DAY), NULL, 'borrowing', 0, 0.00, 'admin', DATE_SUB(NOW(), INTERVAL 1 DAY));

-- 更新库存
UPDATE tb_book SET stock_count = stock_count - 1 WHERE book_id IN (4, 5, 1, 6, 2, 3);
