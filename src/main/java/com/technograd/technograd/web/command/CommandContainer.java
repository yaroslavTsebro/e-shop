package com.technograd.technograd.web.command;

import com.technograd.technograd.web.command.general.user.LoginCommand;
import com.technograd.technograd.web.command.general.user.LogoutCommand;
import com.technograd.technograd.web.command.general.user.RegisterCommand;
import com.technograd.technograd.web.command.manager.category.*;
import com.technograd.technograd.web.command.manager.characteristic.*;
import com.technograd.technograd.web.command.manager.company.*;

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

        commands.put("viewCompanies", new ViewCompany());
        commands.put("createCompany", new CreateCompany());
        commands.put("deleteCompany", new DeleteCompany());
        commands.put("updateCompany", new UpdateCompany());
        commands.put("searchCompanies", new SearchCompany());

        commands.put("viewCharacteristics", new ViewCharacteristic());
        commands.put("createCharacteristic", new CreateCharacteristic());
        commands.put("deleteCharacteristic", new DeleteCharacteristic());
        commands.put("updateCharacteristic", new UpdateCharacteristic());
        commands.put("searchCharacteristics", new SearchCharacteristic());

        commands.put("loginCommand", new LoginCommand());
        commands.put("logoutCommand", new LogoutCommand());
        commands.put("registerCommand", new RegisterCommand());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }

}
