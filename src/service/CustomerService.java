package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    public static CustomerService reference = new CustomerService();
    public static CustomerService getInstance() {
        return reference;
    }

    private final Map<String, Customer> customers;

    public CustomerService() {
        this.customers = new HashMap<>();
    }

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        this.customers.put(email, customer);
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        Collection<Customer> result = new ArrayList<>();
        customers.forEach((key, customer) -> result.add(customer));
        return result;
    }
}
