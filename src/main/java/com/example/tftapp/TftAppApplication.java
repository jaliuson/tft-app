package com.example.tftapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

@SpringBootApplication
@RestController
public class TftAppApplication {
	private static Map<String, String> jsonOutput = new HashMap<>();
	private RiotAPIHelper riotAPIHelper;

	public static void main(String[] args) {
		SpringApplication.run(TftAppApplication.class, args);
	}

	@GetMapping("/hello")
	public ResponseEntity<Collection<String>> sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
		//return String.format("Hello %s!", name); //http://localhost:8080/hello?myName=jason
		jsonOutput.put("name", name);
		return new ResponseEntity<>(jsonOutput.values(), HttpStatus.OK);
	}

	@GetMapping("/description")
	public ResponseEntity<Collection<String>> getProductDescription(){
		jsonOutput.put("App Description", "Valorant Tracker App!");
		return new ResponseEntity<>(jsonOutput.values(), HttpStatus.OK);
	}

	@GetMapping("/match-history")
	public Collection getMatchHistory(@RequestParam(value = "sumName", defaultValue = "player") String summonerName){
		riotAPIHelper = new RiotAPIHelper();
		String puuid = riotAPIHelper.getPuuid(summonerName);
		return (riotAPIHelper.getMatchHistory(puuid));
	}
}
