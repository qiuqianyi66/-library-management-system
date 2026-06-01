# 高校图书管理系统 (Library Management System)

基于 **RuoYi-Vue 3.8.9** 框架构建的商业级高校图书管理系统。

## 🚀 技术栈

| 层级 | 技术 |
|------|------|
| **后端框架** | Spring Boot 2.5.15, MyBatis, Spring Security + JWT |
| **数据库** | MySQL 8.0 |
| **缓存** | Redis (Lettuce) |
| **前端框架** | Vue 2.6 + Element UI 2.15 + Vue Router + Vuex |
| **图表** | ECharts 5.4 |
| **构建工具** | Maven 3.x, Vue CLI 4 |

## ✨ 核心功能

### 📚 图书管理
- 图书录入、编辑、删除、上下架
- 按书名、ISBN、作者、分类多维度检索
- 库存实时管理

### 📖 借阅管理
- 借书/还书/续借全流程
- 读者状态自动校验（逾期检测、借阅上限控制）
- 智能逾期罚款计算

### 💰 罚款与支付
- 逾期自动计算罚款（0.5元/天）
- 支持微信/支付宝在线支付
- 支付订单管理与回调

### 👤 读者自助门户
- 我的借阅：查看当前和历史借阅记录
- 在线续借、图书检索
- 罚款查询与在线缴纳

### 📊 数据看板
- 今日借阅/归还量、在借数量、逾期数量实时统计
- 近7天借阅趋势图表
- 热门图书 TOP10、罚款收入统计

### 🔒 系统安全
- Spring Security + JWT 认证
- 接口限流保护 (Redis)
- XSS 防护、防重复提交

## 🏗️ 项目结构

```
高校图书管理系统/
├── 后端/book-manager-back/
│   ├── ruoyi-admin/        # 控制器层 + 应用入口
│   ├── ruoyi-framework/    # 框架核心（安全、配置、AOP）
│   ├── ruoyi-system/       # 业务逻辑 + Mapper
│   └── ruoyi-common/       # 通用工具、实体、注解
├── 前端/book-manager-ui/   # Vue 2 前端工程
└── data/                    # 数据库初始化 SQL
```

## 📋 环境要求

- **JDK**: 1.8+
- **MySQL**: 8.0+
- **Redis**: 6.0+
- **Node.js**: 12+
- **Maven**: 3.6+

## 🔧 快速开始

### 1. 数据库初始化
```bash
# 导入 data/book-manager.sql 到 MySQL
mysql -u root -p < data/book-manager.sql
```

### 2. 后端启动
```bash
cd 后端/book-manager-back
# 修改 ruoyi-admin/src/main/resources/application.yml 中的数据库和 Redis 配置
mvn clean install -pl ruoyi-admin -am
cd ruoyi-admin
mvn spring-boot:run
```
后端启动于 **http://localhost:8091**

### 3. 前端启动
```bash
cd 前端/book-manager-ui
npm install
npm run dev
```
前端启动于 **http://localhost:82**

### 4. 访问系统
- 默认管理员账号：**admin / admin123**

## 📝 配置说明

核心配置位于 `后端/book-manager-back/ruoyi-admin/src/main/resources/application.yml`：
- 数据库连接：`spring.datasource` 节点
- Redis连接：`spring.redis` 节点
- 文件上传路径：`ruoyi.profile`
- JWT 密钥：`token.secret`

借阅规则可通过系统参数配置页面动态调整（`borrow.max_count`, `fine.per_day` 等）。

## 📦 部署

### 后端部署
```bash
cd 后端/book-manager-back
mvn clean package -pl ruoyi-admin -am
# jar 包位于 ruoyi-admin/target/
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

### 前端部署
```bash
cd 前端/book-manager-ui
npm run build:prod
# 将 dist/ 目录部署到 Nginx
```

## 📄 开源协议

基于 RuoYi-Vue (MIT License)
