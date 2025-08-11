package com.skylena.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skylena.entity.User;
import com.skylena.repository.RegistrationRepository;

@Service
public class RegistrationService {

	@Autowired
	private RegistrationRepository repository;

	// Fetch all users
	public List<User> getAll() {
		return repository.findAll();
	}

	// Add a new user
	public User addData(User skylena) {
		return repository.save(skylena);
	}

	// Fetch a user by ID
	public User getById(Long id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
	}

	// Update an existing user
	public User updateUser(Long id, User updatedUser) {
		User existingUser = getById(id); // Fetch the existing user

		// Update individual fields
		existingUser.setFirstname(updatedUser.getFirstname());
		existingUser.setLastname(updatedUser.getLastname());
		existingUser.setGender(updatedUser.getGender());
		existingUser.setPassword(updatedUser.getPassword());
		existingUser.setEmail(updatedUser.getEmail());

		// Save and return the updated user
		return repository.save(existingUser);
	}

	// Delete a user by ID
	public void deleteUser(Long id) {
		User user = getById(id); // Fetch the user to ensure it exists
		repository.delete(user); // Delete the user
	}
}
