package com.skylena.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skylena.entity.ContactUs;
import com.skylena.service.ContactUsService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth/contact")
public class ContactUsController {

	@Autowired
	private ContactUsService service;

	// Get all records
	@GetMapping("/")
	public ResponseEntity<List<ContactUs>> getAll() {
		List<ContactUs> contacts = service.getAll();
		return ResponseEntity.ok(contacts);
	}

	// Add a new ContactUs record
	@PostMapping("/add")
	public ResponseEntity<ContactUs> addContactUs(@RequestBody ContactUs contactUs) {
		ContactUs createdContact = service.addContactUs(contactUs);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
	}

	// Get a ContactUs record by ID
	@GetMapping("/{id}")
	public ResponseEntity<ContactUs> getById(@PathVariable Long id) {
		try {
			ContactUs contact = service.getById(id);
			return ResponseEntity.ok(contact);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Update an existing ContactUs record
	@PutMapping("/update/{id}")
	public ResponseEntity<ContactUs> updateData(@PathVariable Long id, @RequestBody ContactUs updatedData) {
		try {
			ContactUs updatedContact = service.updateData(id, updatedData);
			return ResponseEntity.ok(updatedContact);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Delete a ContactUs record
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteData(@PathVariable Long id) {
		try {
			service.deleteData(id);
			return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
