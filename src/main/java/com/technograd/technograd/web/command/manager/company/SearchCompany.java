package com.technograd.technograd.web.command.manager.company;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.CompanyDAO;
import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.dao.entity.Company;
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

public class SearchCompany extends Command {

    private static final long serialVersionUID = 4918863306195670390L;
    private static final Logger logger = LogManager.getLogger(SearchCompany.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("SearchCompany execute started");

        String pattern = request.getParameter("pattern");
        if (pattern == null || pattern.isEmpty()) {
            return request.getContextPath() + "/controller?command=viewCompanies";
        }
        logger.debug("Pattern is => " + pattern);
        List<Company> companyList = null;
        try {
            companyList = CompanyDAO.searchCompanies(pattern);
            logger.trace("companyList ->" + companyList);
        } catch (DBException exception) {
            try {
                throw new AppException(exception.getMessage());
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        } finally {
            request.setAttribute("companyList", companyList);
        }
        logger.info("SearchCompany execute finished, path transferred to controller");
        return Path.COMPANY_PAGE;
    }
}
