# Tasks

## 阶段一：数据库与后端基础设施

- [ ] Task 1: 数据库表结构扩展
  - [ ] 1.1 创建 tb_borrow_record 借阅记录表（borrow_id, reader_id, book_id, borrow_date, due_date, return_date, status, renew_count, fine_amount, create_time, update_time）
  - [ ] 1.2 创建 tb_fine_record 罚款记录表（fine_id, borrow_id, reader_id, amount, status, pay_method, pay_time, create_time）
  - [ ] 1.3 创建 tb_payment_order 支付订单表（order_id, fine_id, amount, pay_method, pay_status, trade_no, create_time, pay_time）
  - [ ] 1.4 扩展 tb_book 表（新增 isbn, status, shelf_location, cover_image, description 字段）
  - [ ] 1.5 扩展 tb_reader 表（新增 reader_type, avatar, phone, max_borrow_count, blacklist_status 字段）
  - [ ] 1.6 更新初始化数据 SQL，新增借阅相关字典数据

- [ ] Task 2: 后端图书管理模块（ruoyi-admin + ruoyi-system）
  - [ ] 2.1 创建 BookController.java（图书增删改查、上下架、库存管理接口）
  - [ ] 2.2 创建 BookService.java / BookServiceImpl.java（图书业务逻辑）
  - [ ] 2.3 创建 BookMapper.java + BookMapper.xml（图书数据访问）
  - [ ] 2.4 扩展 ReaderController 新增读者借阅历史查询接口

- [ ] Task 3: 后端借阅管理模块
  - [ ] 3.1 创建 BorrowController.java（借书、还书、续借、查询接口）
  - [ ] 3.2 创建 BorrowService.java / BorrowServiceImpl.java（借阅业务逻辑，含借阅规则校验）
  - [ ] 3.3 创建 BorrowMapper.java + BorrowMapper.xml（借阅数据访问）
  - [ ] 3.4 实现逾期检测与罚款自动计算逻辑

- [ ] Task 4: 后端罚款与支付模块
  - [ ] 4.1 创建 FineController.java（罚款查询、支付接口）
  - [ ] 4.2 创建 FineService.java / FineServiceImpl.java（罚款计算与支付业务）
  - [ ] 4.3 创建 FineMapper.java + FineMapper.xml
  - [ ] 4.4 创建 PaymentOrderMapper.java + PaymentOrderMapper.xml
  - [ ] 4.5 集成支付宝/微信支付SDK（生成支付二维码、支付回调处理）

- [ ] Task 5: Redis 缓存深度优化
  - [ ] 5.1 实现图书分类/热门图书 Redis 缓存（CacheConstants 新增缓存键）
  - [ ] 5.2 实现读者借阅状态 Redis 缓存（减少数据库查询）
  - [ ] 5.3 实现系统配置项 Redis 缓存（borrow_max_count 等）
  - [ ] 5.4 配置接口限流规则（使用现有 RateLimiter 注解，基于 Redis 计数）
  - [ ] 5.5 优化 Token 缓存策略（延长有效期，支持刷新）

## 阶段二：前端核心业务页面

- [ ] Task 6: 前端图书管理页面
  - [ ] 6.1 创建 api/bookmanager/book.js API 封装
  - [ ] 6.2 创建 views/bookmanager/book/index.vue（图书列表页，含搜索、分页、状态筛选）
  - [ ] 6.3 创建图书新增/编辑弹窗组件（含 ISBN、封面图片上传）
  - [ ] 6.4 在 router 中注册图书管理路由，在数据库 menu 表中添加菜单

- [ ] Task 7: 前端借阅管理页面
  - [ ] 7.1 创建 api/bookmanager/borrow.js API 封装
  - [ ] 7.2 创建 views/bookmanager/borrow/index.vue（借阅记录列表）
  - [ ] 7.3 创建 views/bookmanager/borrow/borrowBook.vue（借书操作页，支持扫码/搜索读者和图书）
  - [ ] 7.4 创建 views/bookmanager/borrow/returnBook.vue（还书操作页）
  - [ ] 7.5 在 router 和 menu 中注册借阅管理路由

- [ ] Task 8: 前端罚款管理页面
  - [ ] 8.1 创建 api/bookmanager/fine.js API 封装
  - [ ] 8.2 创建 views/bookmanager/fine/index.vue（罚款记录列表）
  - [ ] 8.3 实现支付功能（弹窗展示支付二维码，轮询支付状态）

- [ ] Task 9: 前端读者自助门户
  - [ ] 9.1 创建 views/portal/myBorrow.vue（我的借阅页面）
  - [ ] 9.2 创建 views/portal/bookSearch.vue（图书检索页面，含分类筛选、关键词搜索）
  - [ ] 9.3 创建 views/portal/myFines.vue（我的罚款页面）
  - [ ] 9.4 创建 api/portal/index.js API 封装
  - [ ] 9.5 在 router 中注册门户路由（角色：读者可见）

## 阶段三：仪表盘与UI优化

- [ ] Task 10: 仪表盘数据看板增强
  - [ ] 10.1 创建后端 DashboardController.java（聚合查询接口：今日借阅/归还量、在借数量、逾期数量、罚款收入、热门图书）
  - [ ] 10.2 重构前端 PanelGroup.vue（动态数据：今日借阅、今日归还、在借数、逾期数）
  - [ ] 10.3 重构 BarChart.vue（近7天借阅趋势）
  - [ ] 10.4 新增热门图书 Top10 榜单组件
  - [ ] 10.5 新增本月罚款收入统计卡片

- [ ] Task 11: 前端 UI/UX 商业化重设计
  - [ ] 11.1 重新设计登录页面（品牌化：左图右表单布局、品牌色系、系统简介）
  - [ ] 11.2 更新全局主题色变量（element-variables.scss）— 使用现代品牌色
  - [ ] 11.3 优化侧边栏样式（Logo区域、菜单选中态、图标风格统一）
  - [ ] 11.4 优化顶部导航栏（面包屑优化、用户头像下拉菜单增强）
  - [ ] 11.5 表格组件统一样式优化（斑马纹、悬停效果、操作按钮组）
  - [ ] 11.6 移动端响应式适配（侧边栏折叠、表格横向滚动、操作按钮自适应）

## 阶段四：测试与部署

- [ ] Task 12: 单元测试与集成测试（TDD）
  - [ ] 12.1 编写 BorrowService 单元测试（借书/还书/续借/逾期计算）
  - [ ] 12.2 编写 FineService 单元测试（罚款计算、支付状态更新）
  - [ ] 12.3 编写 BookService 单元测试（CRUD、库存变更）
  - [ ] 12.4 编写前端关键组件测试

- [ ] Task 13: GitHub 仓库创建与代码推送
  - [ ] 13.1 初始化 Git 仓库，配置 .gitignore
  - [ ] 13.2 编写项目 README.md（架构说明、技术栈、部署指南）
  - [ ] 13.3 创建 GitHub 仓库
  - [ ] 13.4 推送代码到 GitHub

# Task Dependencies
- Task 2, 3, 4 依赖 Task 1（数据库表结构）
- Task 3 依赖 Task 2（借阅需要图书数据）
- Task 4 依赖 Task 3（罚款需要借阅记录）
- Task 6, 7, 8, 9 依赖 Task 2, 3, 4（前端需要后端接口）
- Task 10 依赖 Task 3, 4（仪表盘需要借阅和罚款数据）
- Task 11 可与其他前端任务并行
- Task 12 依赖 Task 2, 3, 4（测试需要业务代码）
- Task 13 在所有功能完成后执行
