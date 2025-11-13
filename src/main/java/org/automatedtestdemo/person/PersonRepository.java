package org.automatedtestdemo.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {
	public Person findByUniqueIdentifier(String identifier);
}
