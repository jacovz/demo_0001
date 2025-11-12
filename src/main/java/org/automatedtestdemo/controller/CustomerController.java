package org.automatedtestdemo.controller;

import org.automatedtestdemo.dto.CustomerDTO;
import org.automatedtestdemo.exception.CustomerNotFoundException;
import org.automatedtestdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/save")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.saveCustomer(customerDTO);
    }

    @GetMapping(value = "/get-all-customers")
    public Page<CustomerDTO> getAllCustomers(Pageable pageable){
        return customerService.getAllCustomers(pageable);
    }

    @GetMapping(value = "/get-by-id/{id}")
    public Object getCustomerById(@PathVariable Long id){
        Optional<CustomerDTO> customer = customerService.getCustomerById(id);
        if (customer.isPresent()){
            return customer.get();
        }else {
           throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteCustomer(@PathVariable Long id){
        Optional<CustomerDTO> customer = customerService.getCustomerById(id);
       if (customer.isPresent()){
            customerService.deleteCustomer(id);
            return "Customer deleted";
       }else {
          throw new CustomerNotFoundException("Customer with ID " + id + " not found");
       }
    }

    @PutMapping(value = "/update/{id}")
    public Object updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        Optional<CustomerDTO> existingCustomer = customerService.getCustomerById(id);

        if (existingCustomer.isPresent()) {
            return customerService.updateCustomer(id, customerDTO);
        } else {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        }
    }

}
