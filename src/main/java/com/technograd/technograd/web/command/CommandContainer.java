package com.technograd.technograd.web.command;

import com.technograd.technograd.web.command.general.CreateCategory;
import com.technograd.technograd.web.command.general.ViewCategory;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("/category", new ViewCategory());
        commands.put("/category?command=add", new CreateCategory());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }

}
