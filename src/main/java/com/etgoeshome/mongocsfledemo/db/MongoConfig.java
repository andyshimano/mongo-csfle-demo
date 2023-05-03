package com.etgoeshome.mongocsfledemo.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import com.mongodb.client.MongoClient;
import com.mongodb.client.internal.MongoClientImpl;
import com.mongodb.internal.build.MongoDriverVersion;
import com.etgoeshome.mongocsfledemo.keymangement.KMSHandler;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.lang.String.format;
import static java.lang.System.getProperty;


@Configuration
@EnableMongoRepositories(basePackages = "com.etgoeshome.mongocsfledemo.respository")
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Value(value = "${spring.data.mongodb.database}")
	private String DB_DATABASE;
	@Value(value = "${spring.data.mongodb.uri}")
	private String DB_CONNECTION;
	@Autowired
	private KMSHandler kmsHandler;
    
	//@Bean
	private MongoDriverInformation  getMongoDriverInfo(){
		return MongoDriverInformation.builder()
				.driverName(MongoDriverVersion.NAME)
				.driverVersion(MongoDriverVersion.VERSION)
				.driverPlatform(format("Java/%s/%s", getProperty("java.vendor", "unknown-vendor"),
						getProperty("java.runtime.version", "unknown-version")))
				.build();
	}
    
	//@Bean
	private MongoClientSettings getMongoClientSettings(){

		kmsHandler.buildOrValidateVault();
		return MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(DB_CONNECTION))
				.build();
	}

	/**
	 * Returns the list of custom converters that will be used by the MongoDB template
	 *
	 **/	
	@Override
	//public CustomConversions customConversions() {
	public MongoCustomConversions customConversions() {
		MongoCustomConversions customConversions = new MongoCustomConversions(
				Arrays.asList(new BinaryToBsonBinaryConverter(),
						new BsonBinaryToBinaryConverter()));
		return customConversions;
	}


	@Override
	//@Bean
	public MongoClient mongoClient() {
		kmsHandler.buildOrValidateVault();
		MongoClient mongoClient = new MongoClientImpl(getMongoClientSettings(),getMongoDriverInfo());
		return mongoClient;
	}

	@Override	
	protected String getDatabaseName() {
		return DB_DATABASE;
	}
}

