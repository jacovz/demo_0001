package org.automatedtestdemo.person;

import org.automatedtestdemo.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/save")
    public Person saveCustomer(@RequestBody Person personDTO){
        return personService.savePerson(personDTO);
    }

    @GetMapping(value = "/get-all")
    public List<Person> getAllPersons(){
        return personService.getAllPersons();
    }

    @GetMapping(value = "/get-by-id/{id}")
    public Person getPersonById(@PathVariable Long id){
        Optional<Person> customer = personService.getPersonById(id);
        if (customer.isPresent()){
            return customer.get();
        }else {
           throw new CustomerNotFoundException("Person with ID " + id + " not found");
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteCustomer(@PathVariable Long id){
        Optional<Person> customer = personService.getPersonById(id);
       if (customer.isPresent()){
            personService.deletePerson(id);
            return "Customer deleted";
       }else {
          throw new CustomerNotFoundException("Person with ID " + id + " not found");
       }
    }

    @PutMapping(value = "/update/{id}")
    public Person updateCustomer(@PathVariable Long id, @RequestBody Person personParam) {
        Optional<Person> existingPerson = personService.getPersonById(id);

        if (existingPerson.isPresent()) {
            return personService.updatePerson(id, personParam);
        } else {
            throw new CustomerNotFoundException("Person with ID " + id + " not found");
        }
    }

}
