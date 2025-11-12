package org.automatedtestdemo.service;

import org.automatedtestdemo.dto.CustomerDTO;
import org.automatedtestdemo.entity.Customer;
import org.automatedtestdemo.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDTO saveCustomer(CustomerDTO customerDTO){
        customerRepository.save(modelMapper.map(customerDTO, Customer.class));
        return customerDTO;
    }

    public Page<CustomerDTO> getAllCustomers(Pageable pageable){
        return customerRepository.findAll(pageable).map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }

    public Optional<CustomerDTO> getCustomerById(Long id){
        return customerRepository.findById(id).map((customer -> modelMapper.map(customer, CustomerDTO.class)));
    }

    public void deleteCustomer(Long id){
        if(customerRepository.existsById(id)){
            customerRepository.deleteById(id);
        }else {
            throw new RuntimeException("Invalid Customer ID");
        }
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            Customer customerEntity = customer.get();
            customerEntity.setFirstName(customerDTO.getFirstName());
            customerEntity.setLastName(customerDTO.getLastName());
            customerEntity.setEmail(customerDTO.getEmail());
            customerEntity.setPhone(customerDTO.getPhone());
            customerEntity.setAddress(customerDTO.getAddress());
            Customer updatedCustomer = customerRepository.save(customerEntity);
            return modelMapper.map(updatedCustomer, CustomerDTO.class);
        }else {
            throw new RuntimeException("Invalid Customer ID");
        }
    }
}
