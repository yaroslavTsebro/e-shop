package com.technograd.technograd.web.command;

import com.technograd.technograd.web.command.customer.AddProductAsElementOfCart;
import com.technograd.technograd.web.command.general.ViewMenuCommand;
import com.technograd.technograd.web.command.general.ViewProductCommand;
import com.technograd.technograd.web.command.general.user.*;
import com.technograd.technograd.web.command.manager.category.*;
import com.technograd.technograd.web.command.manager.characteristic.*;
import com.technograd.technograd.web.command.manager.company.*;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("noCommand", new NoCommand());
        commands.put("changeLanguage", new ChangeLanguage());

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

        commands.put("loginPage", new GetLoginPageCommand());
        commands.put("registerPage", new GetRegisterPage());

        commands.put("loginCommand", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("registerCommand", new RegisterCommand());

        commands.put("viewMenu", new ViewMenuCommand());
        commands.put("viewProductPage", new ViewProductCommand());
        commands.put("addToCart", new AddProductAsElementOfCart());

    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }

}
