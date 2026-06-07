<div align="center">

# 📚 高校图书管理系统

> Vibe Coding 氛围化 · 沉浸式阅读体验平台

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.5.15-brightgreen)
![Vue 2](https://img.shields.io/badge/Vue-2.6-4fc08d)
![Element UI](https://img.shields.io/badge/Element_UI-2.15-409eff)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)
![Redis](https://img.shields.io/badge/Redis-5.0-red)

---

</div>

## ✨ 项目亮点

本项目从传统图书管理系统出发，融入 **Vibe Coding（氛围化设计）** 理念，打造有温度的「线上书屋」体验。

| 传统系统 | 本系统 |
|---------|--------|
| 单调白背景 | 🎨 4 套动态主题（深夜阅读角/复古老书房等） |
| 生硬文字通知 | 🦉 拟人助手「小书灵」温和提醒 |
| 枯燥表格列表 | 📖 毛玻璃卡片 + 悬浮预览 |
| 手动录入图书 | 📚 Google Books API 一键自动填充 |
| 红色错误弹窗 | 💡 柔滑动画温和提示 |
| 冷冰冰的警告 | 🌙 白噪音氛围 + 情绪化交互 |

---

## 🚀 技术栈

### 后端

| 技术 | 用途 |
|------|------|
| **Spring Boot 2.5.15** | 核心框架 |
| **Spring Security + JWT** | 认证授权 |
| **MyBatis + PageHelper** | ORM 与分页 |
| **MySQL 8.0 + Druid** | 数据库与连接池 |
| **Redis + Lettuce** | 缓存加速 |
| **Swagger 3.0** | API 文档 |
| **HttpUtils** | 对接 Google Books API |

### 前端

| 技术 | 用途 |
|------|------|
| **Vue 2.6 + Vue Router** | 前端框架 |
| **Element UI 2.15** | UI 组件库 |
| **ECharts 5.4** | 数据可视化 |
| **CSS Variables** | 动态主题系统 |
| **Webpack** | 构建工具 |

---

## 🎯 核心功能

### 📖 图书管理
- 图书 CRUD（增删改查）
- ISBN 自动填充（对接 Google Books API）
- 上下架状态管理
- 库存实时跟踪

### 📋 借阅管理
- 借书（校验逾期 / 上限 5 本 / 库存）
- 还书（逾期自动计算罚款）
- 续借（上限 2 次，动态配置）
- 并发控制（事务 + 库存同步）

### 💰 罚款管理
- 逾期自动生成罚款记录
- 微信 / 支付宝支付
- 支付回调处理

### 🏠 读者门户
- 图书检索与分类筛选
- ⭐ **图书评分与评论**
- 📊 **个人阅读报告**（ECharts）
- 🤖 **智能推荐**（协同过滤算法）
- 📖 **书籍悬浮预览**（毛玻璃效果）

### 📊 数据看板
- 今日借还统计
- 近 7 天借阅趋势
- 热门图书 TOP10
- 各类型图书分布

---

## 🎨 Vibe Coding 氛围化功能

### 🎭 四套动态主题
| 主题 | 风格 | 色调 |
|------|------|------|
| 现代简约 | 清爽商务 | 蓝色系 |
| 复古老书房 | 温馨文艺 | 暖黄木质 |
| 深夜阅读角 | 沉浸护眼 | 暗色星空 |
| 校园自习馆 | 明亮清新 | 绿色系 |

一键切换，全局 CSS 变量驱动，所有组件联动变色。

### 🦉 拟人助手「小书灵」
- SVG 猫头鹰角色，常驻页面右下角
- 4 种情绪动画：开心 😊 / 思考 🤔 / 打瞌睡 😴 / 发呆
- 气泡消息温和提醒，替代生硬弹窗
- 点击互动，随机卖萌回复

### 🔊 白噪音氛围面板
- 四种场景音效：雨声 / 翻书声 / 咖啡馆 / 深夜
- 声波动画可视化
- 与主题系统联动

### 📖 书籍悬浮预览
- 鼠标悬停图书卡片，弹出毛玻璃便签
- 显示评分 / 库存 / 简介
- 300ms 延迟防误触，平滑动画

### 💡 温和提醒系统
- 全局 `$vibe` API 替代 `$message`
- 柔滑弹出动画，毛玻璃背景
- 四种类型：info / success / warning / gentle

---

## 🏗️ 项目结构

```
高校图书管理系统/
├── 后端/book-manager-back/     # Spring Boot 多模块
│   ├── ruoyi-admin/             # Controller + 入口
│   ├── ruoyi-common/            # 实体 + 工具
│   ├── ruoyi-system/            # Service + Mapper
│   └── ruoyi-framework/         # 安全 + 配置
├── 前端/book-manager-ui/        # Vue 2 前端
│   ├── src/views/               # 页面组件
│   ├── src/components/          # 通用组件
│   ├── src/api/                 # API 封装
│   ├── src/store/               # 状态管理
│   └── src/styles/              # 样式 + 主题
└── data/                        # SQL 初始化脚本
```

---

## 🚦 快速启动

### 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+
- Node.js 12+

### 1️⃣ 数据库初始化
```bash
mysql -u root -p
CREATE DATABASE IF NOT EXISTS `book-manager` CHARACTER SET utf8mb4;
USE `book-manager`;
SOURCE data/book-manager.sql;
```

### 2️⃣ 启动后端
```bash
cd 后端/book-manager-back
mvn install -DskipTests
mvn -f ruoyi-admin/pom.xml spring-boot:run
# 访问 http://localhost:8091
```

### 3️⃣ 启动前端
```bash
cd 前端/book-manager-ui
npm install
npm run dev
# 访问 http://localhost:82
```

### 4️⃣ 登录
| 账号 | 密码 | 角色 |
|------|------|------|
| `admin` | `admin123` | 管理员 |
| `ry` | `admin123` | 读者 |

---

## 📸 功能预览

```


  数据看板                         图书检索门户
  ┌─────────────────┐             ┌─────────────────┐
  │ 📊 今日借阅  📊 在借   │             │ 📖 图书卡片网格    │
  │ 📈 借阅趋势图     │             │ ⭐ 评分 + 悬浮预览  │
  │ 🏆 热门图书TOP10   │             │ 💬 读者评论        │
  └─────────────────┘             │ 🤖 相关推荐        │
                                   └─────────────────┘

  氛围控制台                       小书灵助手
  ┌─────────────────┐             ┌─────────────────┐
  │ 🎨 主题切换      │             │  🦉 猫头鹰       │
  │ 🔊 白噪音        │             │  "今天想读什么书" │
  │ 🦉 助手开关      │             └─────────────────┘
  └─────────────────┘
```

---

## 🤝 致谢

- 基于 [RuoYi-Vue 3.8.9](http://ruoyi.vip/) 脚手架搭建
- 图书数据来源 [Google Books API](https://developers.google.com/books)
- 图表使用 [ECharts](https://echarts.apache.org/)
- UI 组件使用 [Element UI](https://element.eleme.io/)

---

<div align="center">
  <sub>✨ Vibe Coding — 让代码有温度 ✨</sub>
</div>
