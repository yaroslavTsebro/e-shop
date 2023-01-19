package com.technograd.technograd.web.command.manager.company;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.CompanyDAO;
import com.technograd.technograd.dao.CountryDAO;
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
import java.util.List;

public class ViewCompany extends Command {

    private static final long serialVersionUID = 8389809346058200398L;
    private static final Logger logger = LogManager.getLogger(ViewCompany.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ViewCompany execute started");

        List<Company> companyList = null;
        List<Country> countryList = null;
        try {
            companyList = CompanyDAO.getAllCompanies();
            logger.trace("companyList ->" + companyList);
            countryList = CountryDAO.getAllCountries();
            logger.trace("countryList ->" + countryList);
        } catch (DBException exception) {
            try {
                throw new AppException(exception.getMessage());
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        } finally {
            request.setAttribute("companyList", companyList);
            request.setAttribute("countryList", countryList);
        }
        logger.info("ViewCompany execute finished, path transferred to controller");
        return Path.COMPANY_PAGE;
    }
}
