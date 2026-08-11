package com.example.test;

public class CustomerViewModel {
    private CustomerRepository customerRepository;

    public CustomerViewModel(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public void insertCustomer(Customer customer){
        customerRepository.insertCustomer(customer);

    }
    public void updateCustomer(Customer customer){
        customerRepository.updateCustomer(customer);

    }
    public void deletCustomer(Customer customer){
        customerRepository.deletCustomer(customer);

    }
    public void getAllCustomer(CustomerRepository.CustomerCallBack callBack){
        customerRepository.getAllCustomers(callBack);
    }
    public void getid(int id,CustomerRepository.CustomeridCallBack callBack){
        customerRepository.getCustomerid(id,callBack);
    }
}
