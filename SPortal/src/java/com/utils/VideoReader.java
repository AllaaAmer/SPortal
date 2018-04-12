/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yaraYaseen
 */
@WebServlet(name = "VideoReader", urlPatterns = {"/VideoReader"})
public class VideoReader extends HttpServlet {

    private static int count = 0;

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
            out.println("<title>Servlet VideoReader</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VideoReader at " + request.getContextPath() + "</h1>");
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
//        count++;
//        if (count == 2) {
//            application/x-mplayer2
            byte[] pdfBytesArray = (byte[]) request.getSession().getAttribute("loBytesArray");
            String extenstion = (String) request.getSession().getAttribute("extention");
            if (extenstion.equals("mp4")) {
                response.setContentType("video/x-mp4");
            } else if (extenstion.equals("avi")) {
                response.setContentType("video/x-msvideo");
            } else if (extenstion.equals("flv")) {
                response.setContentType("video/x-flv");
            } else if (extenstion.equals("m3u8")) {
                response.setContentType("application/x-mpegURL");
            } else if (extenstion.equals("ts")) {
                response.setContentType("video/MP2T");
            } else if (extenstion.equals("3gp")) {
                response.setContentType("video/3gpp");
            } else if (extenstion.equals("mov")) {
                response.setContentType("video/quicktime");
            } else if (extenstion.equals("wmv")) {
                response.setContentType("video/x-ms-wmv");
            }
            response.setContentLength(pdfBytesArray.length);
            response.getOutputStream().write(pdfBytesArray);
            response.getOutputStream().flush();
            response.getOutputStream().close();
//        }
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
