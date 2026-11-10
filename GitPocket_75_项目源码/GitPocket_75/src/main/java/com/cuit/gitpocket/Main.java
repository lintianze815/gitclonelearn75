package com.cuit.gitpocket;

import com.cuit.gitpocket.data.CommandRepository;
import com.cuit.gitpocket.model.GitCommand;
import com.cuit.gitpocket.service.QuizService;
import com.cuit.gitpocket.service.SearchService;
import com.cuit.gitpocket.store.ProgressStore;

import java.util.List;
import java.util.Scanner;

/**
 * 程序入口：提供浏览、搜索、练习、查看记录四类交互。
 */
public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final SearchService searchService = new SearchService();
    private final QuizService quizService = new QuizService();
    private final ProgressStore progressStore = new ProgressStore();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        printBanner();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    browseByCategory();
                    break;
                case "2":
                    search();
                    break;
                case "3":
                    startQuiz();
                    break;
                case "4":
                    System.out.println(progressStore.summary());
                    break;
                case "0":
                    running = false;
                    System.out.println("已退出，再见。");
                    break;
                default:
                    System.out.println("无效选项，请输入 0-4 之间的数字。");
            }
        }
    }

    private void printBanner() {
        System.out.println("================ GitPocket_75 ================");
        System.out.println("  Git 命令速查与场景练习工具（共 " + CommandRepository.all().size() + " 条命令）");
        System.out.println("=============================================");
    }

    private void printMenu() {
        System.out.println(" 1. 按分类浏览命令");
        System.out.println(" 2. 关键词搜索");
        System.out.println(" 3. 场景练习");
        System.out.println(" 4. 查看学习记录");
        System.out.println(" 0. 退出");
        System.out.print("请选择：");
    }

    private void browseByCategory() {
        List<String> categories = CommandRepository.categories();
        for (int i = 0; i < categories.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + categories.get(i));
        }
        System.out.print("选择分类（回车返回）：");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return;
        }
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("请输入分类编号。");
            return;
        }
        if (index < 0 || index >= categories.size()) {
            System.out.println("分类编号超出范围。");
            return;
        }
        for (GitCommand command : CommandRepository.byCategory(categories.get(index))) {
            printCommand(command);
        }
    }

    private void search() {
        System.out.print("输入关键词（回车返回）：");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) {
            return;
        }
        List<GitCommand> result = searchService.search(keyword);
        if (result.isEmpty()) {
            System.out.println("没有匹配的命令，换个关键词试试。");
            return;
        }
        System.out.println("命中 " + result.size() + " 条：");
        for (GitCommand command : result) {
            printCommand(command);
        }
    }

    private void printCommand(GitCommand command) {
        System.out.println("[" + command.getCategory() + "] " + command.getName());
        System.out.println("  语法：" + command.getSyntax());
        System.out.println("  说明：" + command.getDescription());
        System.out.println("  示例：" + command.getExample());
        System.out.println();
    }

    private void startQuiz() {
        List<QuizService.Question> questions = quizService.pick(5);
        int correct = 0;
        for (int i = 0; i < questions.size(); i++) {
            QuizService.Question question = questions.get(i);
            System.out.println("【第 " + (i + 1) + " 题】" + question.getScene());
            String[] options = question.getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println("  " + (char) ('A' + j) + ". " + options[j]);
            }
            System.out.print("你的选择：");
            String input = scanner.nextLine().trim().toUpperCase();
            int picked = input.isEmpty() ? -1 : input.charAt(0) - 'A';
            if (picked == question.getAnswerIndex()) {
                correct++;
                System.out.println("回答正确。" + question.getExplanation());
            } else {
                System.out.println("回答错误，正确答案是 "
                        + (char) ('A' + question.getAnswerIndex()) + "。"
                        + question.getExplanation());
            }
            System.out.println();
        }
        System.out.println("本轮得分：" + correct + "/" + questions.size());
        progressStore.record(correct, questions.size());
    }
}
