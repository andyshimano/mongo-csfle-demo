package com.etgoeshome.mongocsfledemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//import com.paychex.mongo.community.MongoCsfleCommunityApplication;
import com.etgoeshome.mongocsfledemo.handler.PersonHandler;
import com.etgoeshome.mongocsfledemo.respository.EncryptedPersonRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;

import com.etgoeshome.mongocsfledemo.db.MongoConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
//@EnableMongoRepositories(basePackageClasses = EncryptedPersonRepository.class)
//@ComponentScan(basePackages = {"com.etgoeshome.mongocsfledemo.entity","com.etgoeshome.mongocsfledemo.respository",
	//	"com.etgoeshome.mongocsfledemo.handler","com.etgoeshome.mongocsfledemo.keymangement"})
@ComponentScan({"com.etgoeshome.mongocsfledemo.respository"})
@ComponentScan({"com.etgoeshome.mongocsfledemo.handler"})
public class MongoCsfleDemoApplication implements CommandLineRunner {
	@Autowired
	private PersonHandler handler;

	Logger logger = LoggerFactory.getLogger(MongoCsfleDemoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MongoCsfleDemoApplication.class, args);
		
		//my
		
        //ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
        //MongoCustomConversions = GreetingService greetingService = context.getBean(GreetingService.class);
        //AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		//ctx.register(MongoConfig.class);
		//ctx.refresh();
	
	}
	
	@Override
	public void run(String... args) throws Exception {
		handler.runApplication();
	}

}
