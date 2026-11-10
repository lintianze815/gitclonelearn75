package com.cuit.gitpocket.store;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 学习记录的本地持久化。
 * 记录文件位于用户目录下的 ~/.gitpocket/history.log，每行一轮练习成绩。
 */
public class ProgressStore {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Path file;

    public ProgressStore() {
        this.file = Paths.get(System.getProperty("user.home"), ".gitpocket", "history.log");
    }

    public void record(int correct, int total) {
        String line = LocalDateTime.now().format(FORMATTER) + "  score=" + correct + "/" + total;
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            Files.write(file, java.util.Collections.singletonList(line), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("学习记录写入失败：" + e.getMessage());
        }
    }

    public String summary() {
        if (!Files.exists(file)) {
            return "暂无练习记录，先做一轮练习吧。";
        }
        try {
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            if (lines.isEmpty()) {
                return "暂无练习记录，先做一轮练习吧。";
            }
            StringBuilder builder = new StringBuilder("共 " + lines.size() + " 轮练习，最近三轮：\n");
            int from = Math.max(0, lines.size() - 3);
            for (int i = from; i < lines.size(); i++) {
                builder.append("  ").append(lines.get(i)).append('\n');
            }
            builder.append("历史最高分：").append(bestScore(lines)).append(" 题");
            return builder.toString();
        } catch (IOException e) {
            return "读取学习记录失败：" + e.getMessage();
        }
    }

    private int bestScore(List<String> lines) {
        int best = 0;
        for (String line : lines) {
            int index = line.indexOf("score=");
            if (index < 0) {
                continue;
            }
            String score = line.substring(index + 6);
            int slash = score.indexOf('/');
            if (slash <= 0) {
                continue;
            }
            try {
                best = Math.max(best, Integer.parseInt(score.substring(0, slash)));
            } catch (NumberFormatException ignored) {
                // 记录格式异常时跳过该行，不影响其他统计
            }
        }
        return best;
    }
}
