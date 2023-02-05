package com.technograd.technograd.web.command;

import com.technograd.technograd.web.command.customer.intend.*;
import com.technograd.technograd.web.command.customer.profile.ChangePasswordCommand;
import com.technograd.technograd.web.command.customer.profile.SendConfirmationLink;
import com.technograd.technograd.web.command.general.ViewMenuCommand;
import com.technograd.technograd.web.command.general.ViewProductCommand;
import com.technograd.technograd.web.command.general.user.*;
import com.technograd.technograd.web.command.manager.ViewAdminPanel;
import com.technograd.technograd.web.command.manager.category.*;
import com.technograd.technograd.web.command.manager.characteristic.*;
import com.technograd.technograd.web.command.manager.company.*;
import com.technograd.technograd.web.command.manager.intend.sending.ChangeProductCondition;
import com.technograd.technograd.web.command.manager.intend.sending.UpdateCountOfProductInIntend;
import com.technograd.technograd.web.command.manager.intend.sending.ViewCurrentSending;
import com.technograd.technograd.web.command.manager.intend.sending.ViewSending;

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
        commands.put("getChangePasswordPage", new GetSendCodePage());
        commands.put("sendConfirmationLink", new SendConfirmationLink());
        commands.put("changePasswordCommand", new ChangePasswordCommand());


        commands.put("viewMenu", new ViewMenuCommand());
        commands.put("viewProductPage", new ViewProductCommand());
        commands.put("addToCart", new AddProductAsElementOfCart());

        commands.put("viewCart", new ViewCartPage());
        commands.put("updateProductCountInCart", new UpdateProductCountInCart());
        commands.put("deleteFromCart", new DeleteFromCart());
        commands.put("registerIntend", new RegisterIntend());

        commands.put("viewAdminPanel", new ViewAdminPanel());
        commands.put("viewSending", new ViewSending());
        commands.put("viewCurrentSending", new ViewCurrentSending());
        commands.put("updateProductCountInIntendAsAdmin", new UpdateCountOfProductInIntend());
        commands.put("changeCondition", new ChangeProductCondition());

        commands.put("viewProfilePage", new ViewProfilePage());
        commands.put("viewCurrentIntend", new ViewCurrentIntend());
        commands.put("turnIntendBack", new TurnIntendBack());

    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }

}
