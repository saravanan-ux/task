package com.skylena.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.skylena.entity.User;
import com.skylena.service.RegistrationService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth/register")
public class RegistrationController {

	@Autowired
	private RegistrationService service;

	@GetMapping("/")
	public List<User> GetAll() {
		return service.getAll();
	}

	@PostMapping("/reg")
	public User addData(@RequestBody User skylena) {
		return service.addData(skylena);
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return service.getById(id);
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
		return service.updateUser(id, updatedUser);
	}

	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable Long id) {
		service.deleteUser(id);
		return "User with ID " + id + " has been deleted successfully!";
	}
}
