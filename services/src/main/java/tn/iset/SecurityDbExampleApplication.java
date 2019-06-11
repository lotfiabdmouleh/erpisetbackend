package tn.iset;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import tn.iset.controller.StorageService;




@SpringBootApplication
@EnableScheduling
public class SecurityDbExampleApplication implements CommandLineRunner {
	@Resource
	StorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityDbExampleApplication.class, args);
	}
	@Override
	public void run(String... arg) throws Exception {
	
	}
}
