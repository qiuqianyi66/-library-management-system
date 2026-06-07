# 📚 高校图书管理系统 — 部署文档

> **技术栈**：Spring Boot 2.5.15 + Vue 2 + MySQL 8.0 + Redis 5.0  
> **默认端口**：后端 8091 / 前端 82

---

## 目录

1. [环境要求](#1-环境要求)
2. [快速部署（一键启动）](#2-快速部署一键启动)
3. [手动部署](#3-手动部署)
4. [配置说明](#4-配置说明)
5. [运行验证](#5-运行验证)
6. [常见问题](#6-常见问题)
7. [项目结构](#7-项目结构)

---

## 1. 环境要求

| 组件 | 版本要求 | 用途 |
|------|---------|------|
| JDK | 1.8+（推荐 21） | 运行后端 Spring Boot |
| Maven | 3.6+ | 构建后端 |
| MySQL | 8.0+ | 数据存储 |
| Redis | 5.0+ | 缓存加速 |
| Node.js | 12+ | 运行前端构建 |
| npm | 6+ | 前端包管理 |

---

## 2. 快速部署（一键启动）

### Windows

```bash
# 1. 克隆项目
git clone https://github.com/qiuqianyi66/-library-management-system.git
cd 高校图书管理系统

# 2. 初始化数据库
mysql -u root -p < data/book-manager.sql
mysql -u root -p book-manager < data/seed-data.sql
mysql -u root -p book-manager < data/seed-recent.sql

# 3. 构建后端并启动
cd 后端/book-manager-back
mvn install -DskipTests
mvn -f ruoyi-admin/pom.xml spring-boot:run
# 后端启动成功 → http://localhost:8091

# 4. 新开终端 → 安装前端依赖并启动
cd 前端/book-manager-ui
npm install
npm run dev
# 前端启动成功 → http://localhost:82
```

### Linux / macOS

```bash
# 1. 克隆项目
git clone https://github.com/qiuqianyi66/-library-management-system.git
cd 高校图书管理系统

# 2. 初始化数据库
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS \`book-manager\` CHARACTER SET utf8mb4;"
mysql -u root -p book-manager < data/book-manager.sql
mysql -u root -p book-manager < data/seed-data.sql
mysql -u root -p book-manager < data/seed-recent.sql

# 3. 构建后端
cd 后端/book-manager-back
mvn install -DskipTests
nohup mvn -f ruoyi-admin/pom.xml spring-boot:run > backend.log 2>&1 &

# 4. 构建前端
cd 前端/book-manager-ui
npm install
nohup npm run dev > frontend.log 2>&1 &
```

---

## 3. 手动部署

### 3.1 数据库初始化

```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS `book-manager`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE `book-manager`;

-- 导入表结构 + 基础数据
SOURCE /path/to/data/book-manager.sql;

-- 导入模拟数据（让仪表盘有数据显示）
SOURCE /path/to/data/seed-data.sql;
SOURCE /path/to/data/seed-recent.sql;
```

### 3.2 数据库配置

编辑 `后端/book-manager-back/ruoyi-admin/src/main/resources/application-druid.yml`：

```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://localhost:3306/book-manager?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
        username: root          # 改为你的数据库用户名
        password: 123456        # 改为你的数据库密码
```

### 3.3 Redis 配置

编辑 `后端/book-manager-back/ruoyi-admin/src/main/resources/application.yml`：

```yaml
spring:
  redis:
    host: localhost        # Redis 地址
    port: 6379             # Redis 端口
    password:              # 如果 Redis 有密码，填在这里
    database: 0
```

> **注意**：如果本地没有 Redis，可以注释掉 Redis 相关代码或安装 Redis。  
> Windows 用户可下载 [Redis for Windows](https://github.com/microsoftarchive/redis/releases)。

### 3.4 构建后端

```bash
cd 后端/book-manager-back
mvn install -DskipTests          # 构建所有模块
mvn -f ruoyi-admin/pom.xml spring-boot:run   # 启动
```

生产环境部署（打成 jar 包运行）：

```bash
mvn -f ruoyi-admin/pom.xml package -DskipTests
java -jar ruoyi-admin/target/ruoyi-admin.jar --server.port=8091 &
```

### 3.5 构建前端

```bash
cd 前端/book-manager-ui
npm install                     # 安装依赖
npm run dev                     # 开发环境（热加载）
```

生产环境部署：

```bash
npm run build:prod              # 构建生产包
# 构建产物在 dist/ 目录，部署到 Nginx/Apache：
# 将 dist/ 内容复制到 web 服务器目录即可
```

Nginx 配置示例：

```nginx
server {
    listen 82;
    server_name localhost;

    root /path/to/dist;
    index index.html;

    # 前端路由 history 模式
    location / {
        try_files $uri $uri/ /index.html;
    }

    # 代理后端 API
    location /dev-api/ {
        proxy_pass http://localhost:8091/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

---

## 4. 配置说明

### 4.1 后端关键配置 (`application.yml`)

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| `server.port` | 8091 | 后端端口 |
| `spring.redis.host` | localhost | Redis 地址 |
| `token.expireTime` | 30 | JWT 过期时间(分钟) |
| `ruoyi.profile` | D:/ruoyi/uploadPath | 文件上传路径 |

### 4.2 前端关键配置 (`vue.config.js`)

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| `port` | 82 | 前端端口 |
| `devServer.proxy.target` | http://localhost:8091 | 后端代理地址 |

### 4.3 数据库配置 (`application-druid.yml`)

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| `url` | jdbc:mysql://localhost:3306/book-manager | 数据库连接 |
| `username` | root | 数据库用户 |
| `password` | 123456 | 数据库密码 |

---

## 5. 运行验证

### 5.1 检查后端

```bash
# 健康检查
curl http://localhost:8091/dashboard/stats
# 预期返回：
# {"msg":"操作成功","code":200,"data":{"todayBorrowCount":0,"currentBorrowCount":9,...}}

# 检查日志
# 控制台输出「若依启动成功」即为启动完成
```

### 5.2 检查前端

```bash
# 访问前端页面
curl http://localhost:82/
# 预期返回：HTML 页面，标题为「高校图书管理系统」
```

### 5.3 登录验证

| 账号 | 密码 | 角色 | 权限 |
|------|------|------|------|
| `admin` | `admin123` | 管理员 | 全部功能 |
| `ry` | `admin123` | 读者 | 门户借阅/查询 |

---

## 6. 常见问题

### Q1: 数据库连不上

```
The last packet sent successfully to the server was 0 ms ago.
```

**解决**：检查 `application-druid.yml` 中的用户名和密码是否正确，以及 MySQL 服务是否已启动。

### Q2: Redis 连不上

```
ERR Client sent AUTH, but no password is set
```

**解决**：打开 `application.yml`，将 Redis `password` 清空或设置为你的 Redis 密码。

```yaml
spring:
  redis:
    password:        # 留空 = 无密码
```

### Q3: 前端跨域报错

**解决**：`vue.config.js` 已配置代理，前端通过 `/dev-api/` 前缀访问后端。确保后端在 8091 端口运行。

### Q4: npm install 报错

```bash
# 尝试使用淘宝镜像
npm config set registry https://registry.npmmirror.com
npm install
```

### Q5: 仪表盘数据显示为 0

**解决**：运行模拟数据脚本：

```bash
mysql -u root -p book-manager < data/seed-data.sql
mysql -u root -p book-manager < data/seed-recent.sql
```

### Q6: Google Books API 用不了

**原因**：部分网络环境无法访问 Google 服务。代码已完整实现，换个网络环境即可。

---

## 7. 项目结构

```
高校图书管理系统/
│
├── 后端/book-manager-back/       # Spring Boot 后端
│   ├── ruoyi-admin/               # Controller + 启动入口
│   ├── ruoyi-common/              # 实体类 + 工具类
│   ├── ruoyi-system/              # Service + Mapper + XML
│   └── ruoyi-framework/           # 安全 + 配置 + 拦截器
│
├── 前端/book-manager-ui/          # Vue 2 前端
│   ├── src/views/                 # 页面组件
│   ├── src/components/            # 通用组件
│   ├── src/api/                   # API 封装
│   ├── src/store/                 # Vuex 状态管理
│   └── src/styles/                # 样式/主题
│
├── data/                          # SQL 脚本
│   ├── book-manager.sql           # 表结构 + 基础数据
│   ├── seed-data.sql              # 模拟数据
│   └── seed-recent.sql            # 近期借阅数据
│
└── README.md                     # 项目介绍
```

---

## 附录：项目亮点（简历用）

```
技术栈: Spring Boot + MyBatis + Redis + Vue 2 + ECharts + Element UI

核心功能:
  · 图书管理（CRUD / ISBN 自动填充 / 库存跟踪）
  · 借阅管理（借书 / 还书 / 续借 / 逾期罚款 / 并发控制）
  · 读者门户（图书检索 / 评分评论 / 自助借书 / 个人藏书阁）
  · 数据看板（ECharts 可视化 / 7天趋势 / 热门TOP10）
  · 智能推荐（协同过滤算法 / Redis缓存）
  · Google Books API 对接

Vibe Coding 氛围化:
  · 4 套动态主题（CSS 变量驱动）
  · 拟人助手（SVG 动画 / 情绪系统）
  · 白噪音氛围面板
  · 毛玻璃悬浮预览卡
  · 温和提醒系统
```

---

> 📌 **GitHub 仓库**：`https://github.com/qiuqianyi66/-library-management-system.git`  
> 📧 **如有问题**：请联系项目维护者
