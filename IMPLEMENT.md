# 商业化实施路线图：从代码到收入

> **当前状态**：已完成技术开发，有可运行的系统  
> **目标**：3 个月内完成首单付费客户

---

## 第一步：产品打包（第 1-7 天）

### 1.1 生成可直接运行的安装包

```bash
# 后端打包
cd 后端/book-manager-back
mvn -f ruoyi-admin/pom.xml package -DskipTests
# 得到: ruoyi-admin/target/ruoyi-admin.jar

# 前端打包
cd 前端/book-manager-ui
npm run build:prod
# 得到: dist/ 文件夹
```

**交付物**：一个 `install.zip`，包含：

```
install.zip
├── ruoyi-admin.jar          # 后端程序
├── dist/                    # 前端静态文件  
├── init.sql                 # 初始化SQL（合并book-manager.sql + seed-data.sql）
├── deploy.md                # 部署文档
├── start.bat                # Windows 启动脚本
└── start.sh                 # Linux 启动脚本
```

### 1.2 写启动脚本

**Windows `start.bat`**：
```bat
@echo off
echo === 启动后端 ===
start java -jar ruoyi-admin.jar --server.port=8091
echo === 启动前端 ===
start http://localhost:8091
echo 系统已启动，访问 http://localhost:8091
pause
```

**Linux `start.sh`**：
```bash
#!/bin/bash
nohup java -jar ruoyi-admin.jar --server.port=8091 > backend.log 2>&1 &
echo "系统已启动，访问 http://服务器IP:8091"
```

### 1.3 准备演示环境

在云服务器（阿里云 99元/月 轻量应用服务器就够）上部署一套在线演示：

```
服务器配置: 2核 2G 5M带宽
系统: Ubuntu 20.04
部署内容: 后端jar + 前端dist + MySQL + Redis
演示地址: http://demo.你的域名.com:82/
演示账号: admin / admin123
```

> **阿里云/腾讯云学生优惠**：学生认证后 99元/年

---

## 第二步：找客户（第 1-14 天，同步进行）

### 2.1 列出目标清单

打开 Excel，找 **你所在城市的** 以下学校：

| 类型 | 数量目标 | 获取方式 |
|------|---------|---------|
| 高职高专 | 5 所 | 百度地图搜「XX市职业技术学院」 |
| 民办本科 | 3 所 | 搜「XX市学院」「XX大学」 |
| 中专/技校 | 5 所 | 搜「XX市中等职业学校」 |
| 国际学校 | 2 所 | 搜「XX市国际学校」 |

**找谁谈**（按优先级）：
1. 图书馆馆长 / 副馆长
2. 信息中心/网络中心主任
3. 教务处老师
4. 学校办公室

**联系方式获取**：
- 学校官网 → 机构设置 → 图书馆 → 联系电话/邮箱
- 百度搜「XX学校图书馆 电话」
- 直接去学校图书馆找值班老师

### 2.2 准备销售话术

**电话话术（30秒内说清）**：
```
老师您好，我们是做高校图书管理系统的。
现在很多学校在用我们的系统，免费部署、操作简单。
您看方便加个微信吗？我发个演示链接给您看看。
```

**微信跟进话术**：
```
X老师您好，这是我们系统的在线演示地址：
http://demo.xxx.com  账号: admin 密码: admin123

核心功能：
1️⃣ 图书管理（扫码借还/自动催还）
2️⃣ 读者门户（手机查书/续借/预约）
3️⃣ 数据看板（借阅统计/热门图书）
4️⃣ 一学期免费部署+培训

如果您方便，我可以在线给您演示5分钟。
```

---

## 第三步：MVP 商业化功能补全（第 1-14 天）

在找客户的同时，把以下功能补上（客户最关心的）：

### 3.1 修复数据看板

确保仪表盘所有数据真实可用：

```java
// 今日借阅统计（已在 DashboardController 实现）
GET /dashboard/stats → {
  todayBorrowCount: 3,      // 今日借出
  todayReturnCount: 2,      // 今日归还
  currentBorrowCount: 15,   // 当前在借
  overdueCount: 1           // 当前逾期
}

// 近7天趋势（已在 BorrowMapper.xml 实现）
GET /dashboard/borrowTrend → [
  { date: "06-01", count: 2 },
  { date: "06-02", count: 5 },
  ...
]
```

### 3.2 添加 Excel 导入导出

**后端**（新增 `ExportController.java`）：

```java
@PostMapping("/export/books")
public void exportBooks(HttpServletResponse response) {
    // 使用 RuoYi 自带的 ExcelUtil
    List<Book> list = bookService.selectBookList(new Book());
    ExcelUtil<Book> util = new ExcelUtil<>(Book.class);
    util.exportExcel(response, list, "图书数据");
}

@PostMapping("/import/books")
public AjaxResult importBooks(MultipartFile file) {
    ExcelUtil<Book> util = new ExcelUtil<>(Book.class);
    List<Book> list = util.importExcel(file.getInputStream());
    // 逐条插入
    for (Book book : list) {
        bookService.insertBook(book);
    }
    return success("导入成功 " + list.size() + " 条");
}
```

**前端**（在图书列表页加导入导出按钮）：

```vue
<el-button @click="handleExport" type="success" icon="el-icon-download">
  导出 Excel
</el-button>
<el-upload :action="'/bookmanager/import/books'" :on-success="handleImportSuccess">
  <el-button type="primary" icon="el-icon-upload2">导入 Excel</el-button>
</el-upload>
```

**RuoYi 已经内置了 Excel 工具类**，直接在实体类字段上加 `@Excel` 注解即可：

```java
public class Book extends BaseEntity {
    @Excel(name = "图书名称")
    private String bookName;
    
    @Excel(name = "ISBN编号")
    private String isbn;
    // ...
}
```

### 3.3 添加逾期短信/邮件提醒（MVP 版）

**最简实现**：在系统首页加一个「逾期提醒」按钮，点击后显示逾期列表，管理员手动点击发送通知。

**进阶实现**：对接阿里云短信 API（注册送免费额度）：

```java
// 定时任务：每天检查逾期记录
@Scheduled(cron = "0 0 8 * * ?")  // 每天早上8点
public void checkOverdueAndNotify() {
    List<BorrowRecord> overdueList = borrowMapper.selectOverdueList();
    for (BorrowRecord record : overdueList) {
        // 发送短信提醒（对接阿里云短信）
        smsService.sendOverdueNotice(
            record.getReaderPhone(),
            record.getBookName(),
            record.getOverdueDays()
        );
    }
}
```

---

## 第四步：首单交付流程（客户同意后，3 天完成）

### Day 1：部署

```
上午：购买云服务器（阿里云 2核2G 99元/月）
下午：安装 MySQL + Redis + JDK
      上传 install.zip，执行初始化脚本
      启动后端 + 前端
      验证系统可访问
```

### Day 2：导入数据

```
上午：导出客户现有系统的图书数据
      格式化为 Excel 模板
      通过系统导入功能批量导入
下午：导入读者数据（学生/教师名单）
      配置借阅规则（借阅天数/续借次数/罚款金额）
      创建管理员账号
```

### Day 3：培训 + 验收

```
上午：培训图书馆老师
      1. 如何借书/还书（扫码操作）
      2. 如何管理图书（新增/编辑/下架）
      3. 如何查看数据（仪表盘/报表）
下午：培训读者代表
      1. 如何在手机端查书
      2. 如何自助借书/续借
      3. 如何查看借阅记录
      签验收单 → 收款
```

---

## 第五步：收款与后续

### 报价参考

| 项目 | 价格 | 说明 |
|------|------|------|
| 系统部署费 | 8,000 元 | 一次性，含部署+数据导入+培训 |
| 年服务费 | 3,000 元/年 | 第二年起，含维护+升级+技术支持 |
| 短信套餐 | 0.05 元/条 | 可选，逾期提醒专用 |
| 定制开发 | 另行报价 | 按需求评估 |

### 收款方式

```
签约时付 50%
验收后付 50%
```

---

## 第六步：后续迭代方向（做完首单后）

### 客户最可能提的需求（提前准备）

| 需求 | 开发难度 | 建议 |
|------|---------|------|
| 微信小程序查书 | ⭐⭐⭐ | 外包或自己做，可单独报价 5000+ |
| 对接校园一卡通 | ⭐⭐⭐⭐ | 需要学校提供接口文档 |
| 打印图书条码 | ⭐ | 已有功能，配置打印机即可 |
| 预约功能 | ⭐⭐ | 2-3 天可完成 |
| 手机短信催还 | ⭐ | 对接阿里云短信，1 天 |

---

## 总结：3 个月时间线

```
第1周      第2周      第3周      第4周      第5-8周     第9-12周
┌──────┐  ┌──────┐  ┌──────┐  ┌──────┐  ┌────────┐  ┌────────┐
│打包  │  │补Excel│  │联系  │  │免费部署│  │拜访客户│  │签约    │
│演示  │  │导出  │  │客户  │  │标杆   │  │演示    │  │收款   │
│环境  │  │优化  │  │演示  │  │案例   │  │谈判   │  │续费   │
└──────┘  └──────┘  └──────┘  └──────┘  └────────┘  └────────┘
```

---

> **一句话总结**：这周把安装包打出来，下周开始给本地学校打电话，两周内免费给一家学校装上，三个月内收第一笔钱。
