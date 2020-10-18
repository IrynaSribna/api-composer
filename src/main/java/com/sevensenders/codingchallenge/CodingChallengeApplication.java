package com.sevensenders.codingchallenge;

import com.sevensenders.codingchallenge.domain.ComicsProvider;
import com.sevensenders.codingchallenge.infrastructure.services.PdlLookupService;
import com.sevensenders.codingchallenge.infrastructure.services.XkcdLookupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;


@SpringBootApplication
@AllArgsConstructor
public class CodingChallengeApplication implements CommandLineRunner {

	private ComicsProvider comicsProvider;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CodingChallengeApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
        System.out.println(comicsProvider.provide());
	}
}
