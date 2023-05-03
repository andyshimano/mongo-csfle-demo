package com.etgoeshome.mongocsfledemo.respository;

//import java.beans.JavaBean;

import org.bson.BsonBinary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etgoeshome.mongocsfledemo.entity.EncryptedPerson;

@Repository
public interface EncryptedPersonRepository extends MongoRepository<EncryptedPerson, String> {
	public EncryptedPerson findByFirstName(String firstName);
	public EncryptedPerson findBySsn(BsonBinary ssn);
}
