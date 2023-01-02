package com.technograd.technograd.web.command;

import com.technograd.technograd.web.command.general.LoginCommand;
import com.technograd.technograd.web.command.general.LogoutCommand;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static{
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("noCommand", new NoCommand());
    }

    public static Command get(String commandName){
        if(commandName == null || !commands.containsKey(commandName)){
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}
