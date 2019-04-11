package com.es.phoneshop.web;

import com.es.phoneshop.model.checkout.ArrayListOrderDao;
import com.es.phoneshop.model.checkout.Order;
import com.es.phoneshop.model.checkout.OrderDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewServlet extends HttpServlet {

    private OrderDao orderDao;

    @Override
    public void init() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Order order = getOrder(request);
        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);
    }

    private Order getOrder(HttpServletRequest request) {
        String id = request.getPathInfo().replaceAll("/", "");
        return orderDao.getOrder(id);
    }
}