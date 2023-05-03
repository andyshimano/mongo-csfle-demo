
package com.etgoeshome.mongocsfledemo.handler;

import com.etgoeshome.mongocsfledemo.entity.EncryptedPerson;
import com.etgoeshome.mongocsfledemo.entity.Person;
import com.etgoeshome.mongocsfledemo.entity.PersonEntityHelper;
import com.etgoeshome.mongocsfledemo.respository.EncryptedPersonRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PersonHandler {

	private static final Logger logger = LoggerFactory.getLogger(PersonHandler.class);
	
	@Autowired
	private EncryptedPersonRepository encryptedPersonRepository;

	@Autowired
	private PersonEntityHelper personEntityHelper;

	private void clean() {
		encryptedPersonRepository.deleteAll();
	}

	public void runApplication() {
		clean();
		//Create a couple of non encrypted persons
		Person p1 = new Person("Alice", "Smith",113431222,"+1-114-114-1250","B+" );
		Person p2 = new Person("Bob", "Smith",113771224,"+1-114-114-1251","O+");

		//my
		//personEntityHelper.buildVault();
		
		//Encrypt the Person and save to EncryptedPerson
		EncryptedPerson ep1 = personEntityHelper.getEncrypedPerson(p1);
		EncryptedPerson ep2 = personEntityHelper.getEncrypedPerson(p2);
		//Save persons..
	
		encryptedPersonRepository.saveAll(Arrays.asList(new EncryptedPerson[]{ep1,ep2}));

		// fetch all persons
		logger.debug("Persons found with findAll():");
		logger.debug("-------------------------------");

		List<Person> decryptedPersons = encryptedPersonRepository.findAll()
				.stream().map(ep -> personEntityHelper.getPerson(ep))
				.collect(Collectors.toList());

		for (Person person : decryptedPersons ) {
			logger.debug(person.toString());
		}

		// fetch an individual customer
		logger.debug("Person found with findByFirstName('Alice'):");
		logger.debug("--------------------------------");

		EncryptedPerson findByFirstNamePerson = encryptedPersonRepository.findByFirstName("Alice");
		logger.info("findByFirstNamePerson Equals Alice Success: {}" ,findByFirstNamePerson.getFirstName().equals("Alice"));

		//For Find by SSN we have to first get the binary version of SSN
		EncryptedPerson findBySsn = encryptedPersonRepository.findBySsn(personEntityHelper.getEncryptedSsn(113431222));
		logger.info("findBySsn equals Alice Success: {}" ,personEntityHelper.getPerson(findBySsn).getFirstName().equals("Alice"));

	}
}
