package com.technograd.technograd.web.command.manager.company;

import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.CompanyDAO;
import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.dao.entity.Company;
import com.technograd.technograd.dao.entity.Country;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class UpdateCompany extends Command {

    private static final long serialVersionUID = 1108101221128289750L;
    private static final Logger logger = LogManager.getLogger(UpdateCompany.class.getName());
    private final CategoryDAO categoryDAO;

    public UpdateCompany(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public UpdateCompany() {
        this.categoryDAO = new CategoryDAO();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("UpdateCompany execute started");

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
            CompanyDAO.updateCompany(company);
        } catch (DBException e) {
            logger.error("errorMessage --> " + e);
            throw new AppException(e);
        }
        logger.info("UpdateCompany execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCompanies";
    }
}
