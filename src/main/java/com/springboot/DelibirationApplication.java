package com.springboot;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.springboot.dao.*;
import com.springboot.entities.*;

@SpringBootApplication
public class DelibirationApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DelibirationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		
	}
} 
