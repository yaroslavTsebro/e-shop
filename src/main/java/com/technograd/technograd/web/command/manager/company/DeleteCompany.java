package com.technograd.technograd.web.command.manager.company;

import com.technograd.technograd.dao.CompanyDAO;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DeleteCompany extends Command {

    private static final long serialVersionUID = 2585592730326793675L;
    private final CompanyDAO companyDAO;
    private static final Logger logger = LogManager.getLogger(DeleteCompany.class.getName());

    public DeleteCompany(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public DeleteCompany() {
        this.companyDAO = new CompanyDAO();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("DeleteCompany execute started");
        HttpSession session = request.getSession();

        int id = Integer.parseInt(request.getParameter("delete_by_id"));
        logger.trace("delete_by_id ->" + id);
        try{
            companyDAO.deleteCompany(id);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.company.delete";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + "controller?command=viewCompanies";
        }

        logger.info("DeleteCompany execute finished, path transferred to controller");
        return request.getContextPath() + "controller?command=viewCompanies";
    }
}
