package com.technograd.technograd.web.command.manager.company;

import com.technograd.technograd.dao.CompanyDAO;
import com.technograd.technograd.dao.entity.Company;
import com.technograd.technograd.dao.entity.Country;
import com.technograd.technograd.web.Commands;
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


public class CreateCompany extends Command {

    private static final long serialVersionUID = -5068493456362968676L;
    private final CompanyDAO companyDAO;
    private static final Logger logger = LogManager.getLogger(CreateCompany.class.getName());

    public CreateCompany() {
        companyDAO = new CompanyDAO();
    }
    public CreateCompany(CompanyDAO dao) {
        companyDAO = dao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("CreateCompany execute started");
        String nameUa = request.getParameter("name_ua");
        logger.trace("name_ua ->" + nameUa);
        String nameEn = request.getParameter("name_en");
        logger.trace("name_en ->" + nameEn);
        HttpSession session = request.getSession();

        int countryId = Integer.parseInt(request.getParameter("country_id"));
        logger.trace("country_id ->" + countryId);
        System.out.println(countryId);

        Country country = new Country();
        country.setId(countryId);
        Company company = new Company(nameUa, nameEn, country);
        try {
            companyDAO.createCompany(company);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.company.create";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + Commands.VIEW_COMPANIES;
        }
        logger.info("CreateCompany execute finished, path transferred to controller");
        return request.getContextPath() + Commands.VIEW_COMPANIES;
    }
}
