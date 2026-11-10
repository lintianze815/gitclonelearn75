package com.cuit.gitpocket.model;

/**
 * 一条 Git 命令的数据模型。
 * 命令的语法、说明与示例一并保存，供速查与检索共同使用。
 */
public class GitCommand {

    private final String category;
    private final String name;
    private final String syntax;
    private final String description;
    private final String example;

    public GitCommand(String category, String name, String syntax, String description, String example) {
        this.category = category;
        this.name = name;
        this.syntax = syntax;
        this.description = description;
        this.example = example;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getSyntax() {
        return syntax;
    }

    public String getDescription() {
        return description;
    }

    public String getExample() {
        return example;
    }

    @Override
    public String toString() {
        return name + "  —— " + description;
    }
}
