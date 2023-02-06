package com.technograd.technograd.web.command.manager.company;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CompanyDAO;
import com.technograd.technograd.dao.CountryDAO;
import com.technograd.technograd.dao.entity.Company;
import com.technograd.technograd.dao.entity.Country;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class ViewCompany extends Command {

    private static final long serialVersionUID = 8389809346058200398L;
    private static final Logger logger = LogManager.getLogger(ViewCompany.class.getName());
    private final CompanyDAO companyDAO;
    private final CountryDAO countryDAO;

    public ViewCompany() {
        this.companyDAO = new CompanyDAO();
        this.countryDAO = new CountryDAO();
    }

    public ViewCompany(CompanyDAO companyDAO, CountryDAO countryDAO) {
        this.companyDAO = companyDAO;
        this.countryDAO = countryDAO;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ViewCompany execute started");
        HttpSession session = request.getSession();

        List<Company> companyList = null;
        List<Country> countryList = null;
        try {
            companyList = companyDAO.getAllCompanies();
            logger.trace("companyList ->" + companyList);
            countryList = countryDAO.getAllCountries();
            logger.trace("countryList ->" + countryList);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.company.view";
            session.setAttribute("errorMessage", errorMessage);
            return Path.COMPANY_PAGE;
        } finally {
            request.setAttribute("companyList", companyList);
            request.setAttribute("countryList", countryList);
        }
        logger.info("ViewCompany execute finished, path transferred to controller");
        return Path.COMPANY_PAGE;
    }
}
