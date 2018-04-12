/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.utils;

import com.dlayer.Dao;
import com.dlayer.entities.Student;
import com.dlayer.entities.Users;
import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author egabi1
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    private static final boolean DEBUG = true;

    // The filter configuration object we are associated with. If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;

    public LoginFilter() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        String file = request.getRequestURL().toString();

        //skip the resources library
        if (!request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0); // Proxies.
        }

        Object loggedInUser = session.getAttribute("loggedUser");
        // clear browser cache to get inside this check :D

        if (file.endsWith(request.getContextPath() + "/") || file.contains("web-service") || file.contains("questionair.xhtml") || file.contains("register.xhtml") || file.contains("index.xhtml") || file.equals("/loginServlet") || file.indexOf(".css") >= 0 || file.indexOf(".js") >= 0 || file.indexOf(".eot") >= 0 || file.indexOf(".ttf") >= 0 || file.indexOf(".jpj") >= 0 || file.indexOf(".woff") >= 0 || file.indexOf("/errorpages") >= 0 || file.contains("img")) {

            chain.doFilter(req, res);

        } else {
            if (loggedInUser == null) {
                response.sendRedirect(request.getContextPath() + "/index.xhtml");
//                    response.sendRedirect(request.getContextPath());
            } else {
                request.setCharacterEncoding("UTF-8");

                chain.doFilter(req, res);
            }
        }
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {

    }

}
