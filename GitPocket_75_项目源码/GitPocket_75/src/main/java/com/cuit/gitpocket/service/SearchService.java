package com.cuit.gitpocket.service;

import com.cuit.gitpocket.data.CommandRepository;
import com.cuit.gitpocket.model.GitCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * 关键词检索：在命令名、语法、说明、示例和分类中做不区分大小写的子串匹配。
 */
public class SearchService {

    public List<GitCommand> search(String keyword) {
        List<GitCommand> result = new ArrayList<>();
        String key = keyword == null ? "" : keyword.trim().toLowerCase();
        if (key.isEmpty()) {
            return result;
        }
        for (GitCommand command : CommandRepository.all()) {
            if (matches(command, key)) {
                result.add(command);
            }
        }
        return result;
    }

    private boolean matches(GitCommand command, String key) {
        return contains(command.getName(), key)
                || contains(command.getSyntax(), key)
                || contains(command.getDescription(), key)
                || contains(command.getExample(), key)
                || contains(command.getCategory(), key);
    }

    private boolean contains(String source, String key) {
        return source != null && source.toLowerCase().contains(key);
    }
}
