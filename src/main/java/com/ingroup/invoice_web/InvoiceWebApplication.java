package com.ingroup.invoice_web;

import com.ingroup.invoice_web.config.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(FileProperties.class)
@EnableScheduling
@SpringBootApplication
public class InvoiceWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceWebApplication.class, args);
	}

}
