# 分支管理与冲突解决规范

## 分支模型

| 分支 | 用途 | 保护规则 |
| --- | --- | --- |
| `main` | 稳定版本，每次发布打 tag | 只接受来自 develop 的 PR，需 1 人 Review |
| `develop` | 集成分支，合并已完成的功能 | 只接受来自 feature/fix 的 PR |
| `feature/*` | 单个功能开发 | 从 develop 拉出，完成后合并回 develop |
| `fix/*` | 缺陷修复 | 同上，命名中体现问题编号 |

## 完整流程

```bash
# 1. 从 develop 拉出功能分支
git checkout develop
git pull origin develop
git checkout -b feature/search

# 2. 开发并小步提交
git add src/main/java/com/cuit/gitpocket/service/SearchService.java
git commit -m "feat: 支持中文关键词检索"

# 3. 推送并创建 Pull Request
git push -u origin feature/search

# 4. 合并后删除本地与远程分支
git checkout develop
git merge --no-ff feature/search
git push origin develop
git branch -d feature/search
git push origin --delete feature/search
```

## 冲突的产生与解决

冲突来自两条分支修改了同一文件的同一区域。以 README 的"功能特性"一节为例，`feature/search` 与 `fix/readme-typo` 同时改动了同一行，合并时 Git 无法自动判断保留哪一版：

```bash
git checkout develop
git merge feature/search
# Auto-merging README.md
# CONFLICT (content): Merge conflict in README.md
# Automatic merge failed; fix conflicts and then commit the result.
```

冲突区域会被标记为：

```
<<<<<<< HEAD
- 关键词搜索：支持中文描述检索。
=======
- 关键词搜索：支持中英文混合检索，命中结果高亮显示。
>>>>>>> feature/search
```

处理步骤：

1. `git status` 查看冲突文件，逐个打开定位 `<<<<<<<`、`=======`、`>>>>>>>` 标记。
2. 与两条分支的作者确认取哪种表述，或合并两者的意思，删除全部标记行。
3. `git add README.md` 标记已解决，`git commit` 完成合并提交。
4. 若判断失误需要放弃本次合并，执行 `git merge --abort` 回到合并前状态。

## 约定

- 合并到 develop 一律使用 `--no-ff`，保留功能分支的提交历史，便于回溯。
- 单次 PR 改动不超过 300 行，超过时拆分，降低 Review 成本。
- 发布前在 `main` 打 tag，格式为 `v1.0.0`，与 README 的功能说明同步更新。
