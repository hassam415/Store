package com.example.test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomerRepository {
   public CustomerDAO customerDAO;
ExecutorService executorService= Executors.newSingleThreadExecutor();
    public CustomerRepository(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    public interface CustomeridCallBack{
        void onResult(Customer customer);
    }
    public interface  CustomerCallBack{
        void onResult(List<Customer>customers);
    }
    public void getCustomerid(int id ,CustomeridCallBack callBack){
        executorService.execute(() -> {
            Customer customer=customerDAO.getCustomerid(id);
            callBack.onResult(customer);
        });
    }
    public void getAllCustomers(CustomerCallBack callBack){
        executorService.execute(() -> {
            List<Customer>customers=customerDAO.getAllCustomer();
            callBack.onResult(customers);
        });
    }
    public void insertCustomer(Customer customer){
        executorService.execute(() -> {
            customerDAO.insertCustomer(customer);
        });

    }
    public  void updateCustomer(Customer customer){
        executorService.execute(() -> {
            customerDAO.updateCustomer(customer);
        });


    }
    public void deletCustomer(Customer customer){
        executorService.execute(() -> {
            customerDAO.deletCustomer(customer);
        });


    }


}
