# GitPocket_75

Git 命令速查与场景练习工具（Java 命令行版）

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/java-11%2B-orange.svg)
![PRs](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)

## 项目简介

GitPocket_75 是一个面向 Git 初学者的轻量命令行工具。它把"记不住命令、看得懂文档但上手想不起来用哪一条"的问题拆成三件事：按场景查命令、用关键词搜命令、用选择题练命令。程序只用 JDK 自带类库实现，不需要 Maven、不需要联网，克隆后一次编译即可运行。

项目名称按课程要求采用"项目名称_学号后两位"的形式，`75` 为学号后两位。

## 功能特性

- **分类速查**：内置 24 条常用命令，分为提交、分支、远程、撤销、查看历史、协作六类，每条给出语法、一句话说明和可直接复制的示例。
- **关键词搜索**：在命令名、语法、说明和示例中做不区分大小写的匹配，支持中文描述检索，例如搜索"撤销"。
- **场景练习**：题库按真实协作场景出题（合并冲突、误提交回退、远程分支同步等），四选一作答后立即给出正确答案与解析。
- **学习记录**：每轮练习的成绩写入用户目录下的 `~/.gitpocket/history.log`，下次启动可查看历史最高分。

## 快速开始

环境要求：JDK 11 及以上，无需任何第三方依赖。

```bash
git clone https://github.com/your-account/GitPocket_75.git
cd GitPocket_75
bash run.sh
```

也可以手动编译运行：

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out com.cuit.gitpocket.Main
```

## 目录结构

```
GitPocket_75/
├── README.md                     项目说明
├── LICENSE                       MIT 许可证
├── CONTRIBUTING.md               贡献指南
├── run.sh                        一键编译运行脚本
├── .gitignore
├── docs/
│   └── branch-strategy.md        分支管理与冲突解决规范
├── .github/
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md         Bug 反馈模板
│   │   └── feature_request.md    功能建议模板
│   └── PULL_REQUEST_TEMPLATE.md
└── src/main/java/com/cuit/gitpocket/
    ├── Main.java                 程序入口与交互菜单
    ├── model/GitCommand.java     命令数据模型
    ├── data/CommandRepository.java 命令库
    ├── service/SearchService.java  关键词检索
    ├── service/QuizService.java    场景练习与判分
    └── store/ProgressStore.java    学习记录持久化
```

## 使用示例

启动后进入主菜单：

```
================ GitPocket_75 ================
 1. 按分类浏览命令
 2. 关键词搜索
 3. 场景练习
 4. 查看学习记录
 0. 退出
=============================================
请选择：
```

一次场景练习的作答过程：

```
【第 1 题】你在 feature/search 分支完成开发，需要把远程 main 的最新提交合到当前分支。
  A. git merge origin/main
  B. git checkout main
  C. git push origin main
  D. git reset --hard origin/main
你的选择：A
回答正确。git merge origin/main 会把远程 main 的最新提交合入当前分支，冲突时在本地解决后再提交。
```

## 分支与协作规范

项目采用 `main`、`develop`、`feature/*`、`fix/*` 的分支模型，功能在 feature 分支开发，经 Pull Request 合并到 develop，再由 develop 合并到 main 并打标签。合并冲突的处理步骤与命名规范见 [docs/branch-strategy.md](docs/branch-strategy.md)。

## 贡献

欢迎提交 Issue 说明问题或建议，提交代码前请阅读 [CONTRIBUTING.md](CONTRIBUTING.md)。

## 许可证

本项目采用 [MIT](LICENSE) 许可证。
