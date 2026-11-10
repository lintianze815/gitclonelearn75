package com.cuit.gitpocket.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 场景练习：每轮从题库中随机抽取若干题目，逐题作答并即时给出解析。
 */
public class QuizService {

    /** 一道题目：场景描述、四个选项、正确项下标与解析。 */
    public static class Question {
        private final String scene;
        private final String[] options;
        private final int answerIndex;
        private final String explanation;

        public Question(String scene, String[] options, int answerIndex, String explanation) {
            this.scene = scene;
            this.options = options;
            this.answerIndex = answerIndex;
            this.explanation = explanation;
        }

        public String getScene() {
            return scene;
        }

        public String[] getOptions() {
            return options;
        }

        public int getAnswerIndex() {
            return answerIndex;
        }

        public String getExplanation() {
            return explanation;
        }
    }

    private final List<Question> questions = new ArrayList<>();

    public QuizService() {
        load();
    }

    public List<Question> pick(int count) {
        List<Question> copy = new ArrayList<>(questions);
        Collections.shuffle(copy, new Random());
        int size = Math.min(count, copy.size());
        return new ArrayList<>(copy.subList(0, size));
    }

    private void load() {
        add("你在 feature/search 分支完成开发，需要把远程 main 的最新提交合到当前分支。",
                new String[]{"git merge origin/main", "git checkout main", "git push origin main", "git reset --hard origin/main"},
                0,
                "git merge origin/main 会把远程 main 的最新提交合入当前分支，冲突时在本地解决后再提交。");
        add("提交信息写错了，但还没有推送到远程，希望保留改动重新写说明。",
                new String[]{"git commit --amend", "git reset --hard HEAD~1", "git revert HEAD", "git push -f origin main"},
                0,
                "git commit --amend 可修改最近一次提交，未推送时使用最安全。");
        add("需要临时切到其他分支处理紧急问题，当前改动还不想提交。",
                new String[]{"git stash", "git commit -m \"临时\"", "git reset --hard", "git clean -fd"},
                0,
                "git stash 把未提交的改动暂存起来，处理完紧急问题后 git stash pop 恢复。");
        add("本地分支已落后于远程，想先拿到远程更新再继续开发。",
                new String[]{"git fetch origin", "git push origin develop", "git branch -d develop", "git tag v1.0.0"},
                0,
                "git fetch 只下载远程更新而不改动工作区，便于先比较再决定是否合并。");
        add("已经推送到远程的提交引入了 bug，团队希望保留历史并撤销它的影响。",
                new String[]{"git revert <提交号>", "git reset --hard <提交号>", "git rebase -i HEAD~3", "git checkout <提交号>"},
                0,
                "git revert 生成一个反向提交，不改写已推送历史，团队协作更安全。");
        add("想让 develop 的提交历史保持线性，把自己的功能分支接到 develop 最新提交之后。",
                new String[]{"git rebase develop", "git merge develop", "git cherry-pick develop", "git pull --rebase=false"},
                0,
                "git rebase develop 把当前分支的提交在 develop 之上重放，得到线性历史。");
        add("要发布 1.0 版本，希望在仓库中留下一个可回溯的版本标记。",
                new String[]{"git tag v1.0.0", "git branch v1.0.0", "git commit -m \"v1.0.0\"", "git push origin main"},
                0,
                "git tag 为特定提交打标签，发布版本通常用 tag 而不是普通提交说明。");
        add("合并时提示 CONFLICT，需要先确认哪些文件存在冲突。",
                new String[]{"git status", "git log --all", "git diff HEAD", "git remote -v"},
                0,
                "git status 会在 “Unmerged paths” 下列出冲突文件，处理完再 git add 标记已解决。");
    }

    private void add(String scene, String[] options, int answerIndex, String explanation) {
        questions.add(new Question(scene, options, answerIndex, explanation));
    }
}
