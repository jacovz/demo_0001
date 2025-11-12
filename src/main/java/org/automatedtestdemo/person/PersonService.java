package org.automatedtestdemo.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person savePerson(Person person){
        personRepository.save(person);
        return person;
    }

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Long id){
        return personRepository.findById(id);
    }

    public void deletePerson(Long id){
        if(personRepository.existsById(id)){
            personRepository.deleteById(id);
        }else {
            throw new RuntimeException("Invalid Person ID");
        }
    }

    public Person updatePerson(Long id, Person customerDTO){
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()){
            Person personEntity = person.get();
            personEntity.setFirstName(customerDTO.getFirstName());
            personEntity.setLastName(customerDTO.getLastName());
            personEntity.setEmail(customerDTO.getEmail());
            personEntity.setPhone(customerDTO.getPhone());
            personEntity.setAddress(customerDTO.getAddress());
            Person updatedPerson = personRepository.save(personEntity);
            return updatedPerson;
        }else {
            throw new RuntimeException("Invalid Person ID");
        }
    }
}
