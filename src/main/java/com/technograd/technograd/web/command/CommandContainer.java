package com.technograd.technograd.web.command;

import com.technograd.technograd.web.command.customer.category.*;
import com.technograd.technograd.web.command.manager.category.*;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("noCommand", new NoCommand());

        commands.put("viewCategories", new ViewCategory());
        commands.put("createCategory", new CreateCategory());
        commands.put("deleteCategory", new DeleteCategory());
        commands.put("updateCategory", new UpdateCategory());
        commands.put("searchCategories", new SearchCategory());

    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }

}
