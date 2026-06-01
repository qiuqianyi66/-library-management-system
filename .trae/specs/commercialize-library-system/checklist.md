# Checklist

## 数据库层
- [ ] tb_borrow_record 表已创建且字段完整（borrow_id, reader_id, book_id, borrow_date, due_date, return_date, status, renew_count, fine_amount）
- [ ] tb_fine_record 表已创建且字段完整（fine_id, borrow_id, reader_id, amount, status, pay_method, pay_time）
- [ ] tb_payment_order 表已创建且字段完整
- [ ] tb_book 表已扩展 isbn, status, shelf_location, cover_image, description 字段
- [ ] tb_reader 表已扩展 reader_type, avatar, phone, max_borrow_count, blacklist_status 字段
- [ ] 初始化 SQL 数据可正常导入 MySQL

## 后端 - 图书管理
- [ ] BookController 提供 GET/POST/PUT/DELETE 完整 CRUD 接口
- [ ] 图书支持按书名、作者、ISBN、分类模糊搜索
- [ ] 图书上下架状态切换接口正常
- [ ] 图书库存数量在借阅/归还时正确增减

## 后端 - 借阅管理
- [ ] BorrowController 提供借书、还书、续借、查询接口
- [ ] 借书时校验：读者无逾期未还、未超借阅上限、图书有库存
- [ ] 还书时自动计算逾期天数并生成罚款记录
- [ ] 续借限制：未逾期、未超过最大续借次数

## 后端 - 罚款支付
- [ ] FineController 提供罚款查询和支付接口
- [ ] 逾期罚款按日计算正确（每日0.5元）
- [ ] 支付订单生成和回调处理流程正确
- [ ] 罚款状态流转正确：未缴纳 -> 已缴纳

## 后端 - Redis 缓存
- [ ] 图书分类/热门图书列表已缓存到 Redis
- [ ] 读者借阅状态已缓存
- [ ] 系统配置项（借阅规则）已缓存且支持动态刷新
- [ ] 接口限流注解已配置在核心接口上
- [ ] Redis 缓存过期策略合理（热点数据30分钟，配置60分钟）

## 前端 - 图书管理页
- [ ] 图书列表页展示：书名、ISBN、作者、分类、库存、状态
- [ ] 支持搜索和分类筛选
- [ ] 新增/编辑弹窗表单完整（含封面图片上传）
- [ ] 上下架操作正常

## 前端 - 借阅管理页
- [ ] 借阅记录列表展示完整
- [ ] 借书操作页可搜索读者和图书
- [ ] 还书操作页可快速完成还书
- [ ] 续借操作正常

## 前端 - 罚款管理页
- [ ] 罚款记录列表展示完整
- [ ] 支付弹窗可展示支付二维码
- [ ] 支付状态轮询正常

## 前端 - 读者自助门户
- [ ] 我的借阅页面展示当前和历史借阅
- [ ] 图书检索页面支持搜索和筛选
- [ ] 我的罚款页面展示个人罚款记录
- [ ] 读者角色只能访问门户页面，不能访问管理页面

## 前端 - 数据看板
- [ ] 首页实时展示：今日借阅量、今日归还量、在借数量、逾期数量
- [ ] 近7天借阅趋势图表正确
- [ ] 热门图书 TOP10 榜单正确
- [ ] 本月罚款收入统计正确

## 前端 - UI/UX
- [ ] 登录页品牌化设计完整（Logo、背景、布局）
- [ ] 全局主题色统一，视觉风格现代
- [ ] 侧边栏和导航栏样式优化
- [ ] 表格组件统一样式（斑马纹、悬停效果）
- [ ] 移动端响应式布局正常

## 测试
- [ ] BorrowService 单元测试全部通过
- [ ] FineService 单元测试全部通过
- [ ] BookService 单元测试全部通过
- [ ] 前端关键组件测试通过

## 部署
- [ ] .gitignore 配置正确（排除 node_modules、target、uploadPath、日志）
- [ ] README.md 包含完整的架构说明、技术栈、部署指南
- [ ] GitHub 仓库已创建
- [ ] 代码已推送至 GitHub
- [ ] 前端 dev 模式可正常启动并与后端联调
- [ ] 后端可正常启动并连接 MySQL 和 Redis
