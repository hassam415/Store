package com.example.test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRepository {
    OrderDAO orderDAO;

    public OrderRepository(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }
    ExecutorService executorService= Executors.newSingleThreadExecutor();
    public void insertOrder(Order order){
        executorService.execute(() -> {
            orderDAO.insertOrder(order);
        });

    }
    public interface OrderListCallBack{
        void onresult(List<Order>list);

    }
    public interface OrderIdCallBack{
        void onresult(Order order);
    }
    public void getAllOrders(OrderListCallBack callBack){
        executorService.execute(() -> {
            List<Order> orderList=orderDAO.getAllorder();
            callBack.onresult(orderList);
        });
    }
    public void getOrderId(OrderIdCallBack callBack){
        executorService.execute(() -> {
            Order order=orderDAO.getOrderId();
            callBack.onresult(order);
        });
    }
}
