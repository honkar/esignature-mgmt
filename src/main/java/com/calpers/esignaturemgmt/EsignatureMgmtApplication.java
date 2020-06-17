package com.calpers.esignaturemgmt;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.calpers.esignaturemgmt.web.MainController;
import com.calpers.esignaturemgmt.web.SignatureController;

@SpringBootApplication
@ComponentScan({"com.calpers.esignaturemgmt","com.calpers.esignaturemgmt.web"})
public class EsignatureMgmtApplication {
	

	public static void main(String[] args) {
		new File(SignatureController.uploadDirectory).mkdir();
		
		
		SpringApplication.run(EsignatureMgmtApplication.class, args);
	}

}
