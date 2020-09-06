package com.spring.boot.rocks.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.boot.rocks.model.AppUser;
import com.spring.boot.rocks.repository.AppUserJPARepository;

@RestController
@RequestMapping(path = "/api")
public class AppUserRESTController {
	@Autowired
	private AppUserJPARepository appUserJPARepository;

	@GetMapping(path = "/listappusers", produces = "application/json")
	public List<AppUser> getEmployees() {
		return appUserJPARepository.findAll();
	}

	@GetMapping("/findappuser/{id}")
	public Optional<AppUser> getUser(@PathVariable long id) {
		return appUserJPARepository.findById(id);
	}

	@GetMapping("/get-data")
	public ResponseEntity<Map<String, Integer>> getPieChart() {
		List<AppUser> users = appUserJPARepository.findAll();
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int currentyear = localDate.getYear();
		Map<String, Integer> graphData = new TreeMap<>();
		for (AppUser u : users) {

			graphData.put(u.getUserfirstname() + " " + u.getUserlastname(),
					currentyear - getYear(u.getUserdatecreated()));
//			graphData.put("2016", 147);
//			graphData.put("2017", 1256);
//			graphData.put("2018", 3856);
//			graphData.put("2019", 19807);
		}
		return new ResponseEntity<>(graphData, HttpStatus.OK);
	}
	

	@DeleteMapping(value = "/delete/{id}")
	public String deleteAppUser(@PathVariable Long id) {
		System.out.println("Deleting User :: " + id);
		appUserJPARepository.deleteById(id);
		return "OK!";
	}
	


	public int getYear(Date date) {
		// Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		// int month = localDate.getMonthValue();
		// int day = localDate.getDayOfMonth();
		// System.out.println("Current Year is - " + year);
		return year;
	}
}
