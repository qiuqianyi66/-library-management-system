# Tasks

## 阶段一：数据库与后端基础设施

- [x] Task 1: 数据库表结构扩展
  - [x] 1.1 创建 tb_borrow_record 借阅记录表
  - [x] 1.2 创建 tb_fine_record 罚款记录表
  - [x] 1.3 创建 tb_payment_order 支付订单表
  - [x] 1.4 扩展 tb_book 表（新增 isbn, status, shelf_location, cover_image, description 字段）
  - [x] 1.5 扩展 tb_reader 表（新增 reader_type, avatar, phone, max_borrow_count, blacklist_status 字段）
  - [x] 1.6 更新初始化数据 SQL，新增借阅相关字典数据

- [x] Task 2: 后端图书管理模块（ruoyi-admin + ruoyi-system）
  - [x] 2.1 创建 BookController.java
  - [x] 2.2 创建 BookService.java / BookServiceImpl.java
  - [x] 2.3 创建 BookMapper.java + BookMapper.xml
  - [x] 2.4 扩展 ReaderController 新增读者借阅历史查询接口

- [x] Task 3: 后端借阅管理模块
  - [x] 3.1 创建 BorrowController.java
  - [x] 3.2 创建 BorrowService.java / BorrowServiceImpl.java
  - [x] 3.3 创建 BorrowMapper.java + BorrowMapper.xml
  - [x] 3.4 实现逾期检测与罚款自动计算逻辑

- [x] Task 4: 后端罚款与支付模块
  - [x] 4.1 创建 FineController.java
  - [x] 4.2 创建 FineService.java / FineServiceImpl.java
  - [x] 4.3 创建 FineMapper.java + FineMapper.xml
  - [x] 4.4 创建 PaymentOrderMapper.java + PaymentOrderMapper.xml
  - [x] 4.5 集成支付SDK（模拟支付流程）

- [x] Task 5: Redis 缓存深度优化
  - [x] 5.1 实现图书分类/热门图书 Redis 缓存（CacheConstants 新增缓存键）
  - [x] 5.2 实现读者借阅状态 Redis 缓存
  - [x] 5.3 实现系统配置项 Redis 缓存
  - [x] 5.4 配置接口限流规则
  - [x] 5.5 优化 Token 缓存策略

## 阶段二：前端核心业务页面

- [x] Task 6: 前端图书管理页面
  - [x] 6.1 创建 api/bookmanager/book.js API 封装
  - [x] 6.2 创建 views/bookmanager/book/index.vue
  - [x] 6.3 创建图书新增/编辑弹窗组件
  - [x] 6.4 在 router 中注册图书管理路由

- [x] Task 7: 前端借阅管理页面
  - [x] 7.1 创建 api/bookmanager/borrow.js API 封装
  - [x] 7.2 创建 views/bookmanager/borrow/index.vue
  - [x] 7.3 创建借书操作功能
  - [x] 7.4 创建还书操作功能
  - [x] 7.5 在 router 和 menu 中注册借阅管理路由

- [x] Task 8: 前端罚款管理页面
  - [x] 8.1 创建 api/bookmanager/fine.js API 封装
  - [x] 8.2 创建 views/bookmanager/fine/index.vue
  - [x] 8.3 实现支付功能

- [x] Task 9: 前端读者自助门户
  - [x] 9.1 创建 views/portal/myBorrow.vue
  - [x] 9.2 创建 views/portal/bookSearch.vue
  - [x] 9.3 创建 views/portal/myFines.vue
  - [x] 9.4 创建 api/portal/index.js API 封装
  - [x] 9.5 在 router 中注册门户路由

## 阶段三：仪表盘与UI优化

- [x] Task 10: 仪表盘数据看板增强
  - [x] 10.1 创建后端 DashboardController.java
  - [x] 10.2 重构前端 PanelGroup.vue
  - [x] 10.3 重构 BarChart.vue/LineChart.vue/PieChart.vue
  - [x] 10.4 新增热门图书 Top10 榜单组件
  - [x] 10.5 新增本月罚款收入统计卡片

- [x] Task 11: 前端 UI/UX 商业化重设计
  - [x] 11.1 重新设计登录页面（左图右表单布局）
  - [x] 11.2 更新全局主题色变量
  - [x] 11.3 优化侧边栏样式
  - [x] 11.4 优化导航栏
  - [x] 11.5 表格组件统一样式优化
  - [x] 11.6 移动端响应式适配

## 阶段四：测试与部署

- [x] Task 12: 单元测试与集成测试（TDD）
  - [x] 12.1 编写 BorrowService 单元测试
  - [x] 12.2 编写 FineService 单元测试
  - [x] 12.3 编写 BookService 单元测试
  - [x] 12.4 编译验证通过

- [x] Task 13: GitHub 仓库创建与代码推送
  - [x] 13.1 初始化 Git 仓库，配置 .gitignore
  - [x] 13.2 编写项目 README.md
  - [ ] 13.3 创建 GitHub 仓库（需要手动操作：gh CLI 未安装，请在 GitHub.com 创建仓库后执行 git remote add origin + git push）
  - [ ] 13.4 推送代码到 GitHub
