package com.cuit.gitpocket.data;

import com.cuit.gitpocket.model.GitCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 内置命令库。
 * 按"提交、分支、远程、撤销、查看历史、协作"六类组织，共 24 条常用命令。
 */
public final class CommandRepository {

    private static final List<GitCommand> COMMANDS = new ArrayList<>();

    static {
        // 提交
        add("提交", "git status", "git status", "查看工作区与暂存区相对最近一次提交的变化", "git status");
        add("提交", "git add", "git add <文件> 或 git add .", "把工作区的修改加入暂存区，`.` 表示当前目录下全部改动", "git add src/main/java");
        add("提交", "git commit", "git commit -m \"说明\"", "把暂存区内容记录为一次提交，说明需写明做了什么", "git commit -m \"feat: 支持中文检索\"");
        add("提交", "git commit --amend", "git commit --amend", "修改最近一次提交的信息或内容，仅限尚未推送的提交", "git commit --amend -m \"fix: 修正说明文字\"");
        add("提交", "git diff", "git diff 或 git diff --staged", "查看尚未暂存的改动；加 `--staged` 查看已暂存待提交的改动", "git diff --staged");

        // 分支
        add("分支", "git branch", "git branch [-a] [-d 分支名]", "列出本地分支；`-a` 含远程分支，`-d` 删除已合并分支", "git branch -a");
        add("分支", "git checkout", "git checkout <分支名> 或 git checkout -b <分支名>", "切换分支；加 `-b` 同时创建并切换到新分支", "git checkout -b feature/search");
        add("分支", "git switch", "git switch <分支名> 或 git switch -c <分支名>", "Git 2.23 起用于切换分支的新命令，语义比 checkout 更明确", "git switch -c feature/quiz");
        add("分支", "git merge", "git merge <分支名>", "把指定分支的提交合入当前分支，产生合并提交", "git merge --no-ff feature/search");
        add("分支", "git log --graph", "git log --graph --oneline --all", "以图形方式查看分支走向与合并历史", "git log --graph --oneline --all");

        // 远程
        add("远程", "git clone", "git clone <仓库地址>", "把远程仓库完整复制到本地，同时自动配置 origin", "git clone https://github.com/your-account/GitPocket_75.git");
        add("远程", "git remote", "git remote -v", "查看已配置的远程仓库地址", "git remote -v");
        add("远程", "git push", "git push [-u] origin <分支名>", "把本地提交推送到远程；首次推送加 `-u` 建立跟踪关系", "git push -u origin feature/search");
        add("远程", "git pull", "git pull origin <分支名>", "拉取远程更新并合并到当前分支，等价于 fetch 加 merge", "git pull origin develop");

        // 撤销
        add("撤销", "git restore", "git restore <文件> 或 git restore --staged <文件>", "丢弃工作区改动；加 `--staged` 把文件移出暂存区", "git restore --staged README.md");
        add("撤销", "git reset --soft", "git reset --soft HEAD~1", "撤销最近一次提交并保留改动在暂存区，适合提交信息写错时重来", "git reset --soft HEAD~1");
        add("撤销", "git reset --hard", "git reset --hard <提交号>", "把工作区与暂存区强制回到指定提交，未提交的改动会丢失", "git reset --hard HEAD~1");
        add("撤销", "git revert", "git revert <提交号>", "生成一个新提交来抵消指定提交，已推送的历史用它比 reset 安全", "git revert 3f9a1c2");

        // 查看历史
        add("查看历史", "git log", "git log --oneline -n 10", "查看提交历史，`--oneline` 精简输出，`-n` 限制条数", "git log --oneline -n 10");
        add("查看历史", "git show", "git show <提交号>", "查看某次提交的改动内容与提交信息", "git show 3f9a1c2");
        add("查看历史", "git blame", "git blame <文件>", "逐行查看文件的最后修改者与提交，用于追溯问题来源", "git blame README.md");

        // 协作
        add("协作", "git rebase", "git rebase <分支名>", "把当前分支的提交在目标分支之上重放，得到线性历史", "git rebase develop");
        add("协作", "git stash", "git stash 或 git stash pop", "临时保存未提交的改动，`pop` 恢复并删除该暂存记录", "git stash");
        add("协作", "git tag", "git tag v1.0.0 或 git push origin v1.0.0", "为发布版本打标签并推送到远程", "git tag v1.0.0");
    }

    private CommandRepository() {
    }

    private static void add(String category, String name, String syntax, String description, String example) {
        COMMANDS.add(new GitCommand(category, name, syntax, description, example));
    }

    public static List<GitCommand> all() {
        return Collections.unmodifiableList(COMMANDS);
    }

    public static List<String> categories() {
        Set<String> set = new LinkedHashSet<>();
        for (GitCommand command : COMMANDS) {
            set.add(command.getCategory());
        }
        return new ArrayList<>(set);
    }

    public static List<GitCommand> byCategory(String category) {
        List<GitCommand> result = new ArrayList<>();
        for (GitCommand command : COMMANDS) {
            if (command.getCategory().equals(category)) {
                result.add(command);
            }
        }
        return result;
    }
}
