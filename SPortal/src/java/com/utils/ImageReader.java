/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yaraYaseen
 */
@WebServlet(name = "ImageReader", urlPatterns = {"/ImageReader"})
public class ImageReader extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ImageReader</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ImageReader at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        byte[] pdfBytesArray = (byte[]) request.getSession().getAttribute("loBytesArray");
        request.getSession().removeAttribute("loBytesArray");
        String extenstion = (String) request.getSession().getAttribute("extention");
        if (extenstion.equals("jpg") || extenstion.equals("jpeg")) {
            response.setContentType("image/jpeg");
        }else if (extenstion.equals("gif")) {
            response.setContentType("image/gif");
        }if (extenstion.equals("bmp")) {
            response.setContentType("image/x-windows-bmp");
        }if (extenstion.equals("tiff")) {
            response.setContentType("image/x-tiff");
        }if (extenstion.equals("png")) {
            response.setContentType("image/png");
        }
        response.setContentLength(pdfBytesArray.length);
        response.getOutputStream().write(pdfBytesArray);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
