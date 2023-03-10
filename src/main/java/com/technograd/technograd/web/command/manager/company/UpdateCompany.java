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

public class UpdateCompany extends Command {

    private static final long serialVersionUID = 1108101221128289750L;
    private static final Logger logger = LogManager.getLogger(UpdateCompany.class.getName());
    private final CompanyDAO companyDAO;

    public UpdateCompany(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public UpdateCompany() {
        this.companyDAO = new CompanyDAO();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("UpdateCompany execute started");
        HttpSession session = request.getSession();

        int id = Integer.parseInt(request.getParameter("update_by_id"));
        logger.trace("update_by_id ->" + id);
        String nameUa = request.getParameter("updated_name_ua");
        logger.trace("updated_name_ua ->" + nameUa);
        String nameEn = request.getParameter("updated_name_en");
        logger.trace("updated_name_en ->" + nameEn);

        int countryId = Integer.parseInt(request.getParameter("updated_country_id"));
        logger.trace("updated_country_id ->" + countryId);

        Country country = new Country();
        country.setId(countryId);
        Company company = new Company(id ,nameUa, nameEn, country);
        try {
            companyDAO.updateCompany(company);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.company.update";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + Commands.VIEW_COMPANIES;
        }
        logger.info("UpdateCompany execute finished, path transferred to controller");
        return request.getContextPath() + Commands.VIEW_COMPANIES;
    }
}
