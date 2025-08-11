package com.skylena.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skylena.entity.ContactUs;
import com.skylena.repository.ContactUsRepository;

@Service
public class ContactUsService {

	@Autowired
	private ContactUsRepository repository;

	// Get all records
	public List<ContactUs> getAll() {
		return repository.findAll();
	}

	// Get a record by ID
	public ContactUs getById(Long id) {
		Optional<ContactUs> contact = repository.findById(id);
		if (contact.isPresent()) {
			return contact.get();
		}
		throw new RuntimeException("Record not found for ID: " + id);
	}

	// Update an existing record
	public ContactUs updateData(Long id, ContactUs updatedData) {
		ContactUs existing = getById(id); // Ensure the record exists
		existing.setFirstname(updatedData.getFirstname());
		existing.setLastname(updatedData.getLastname());
		existing.setCountry(updatedData.getCountry());
		existing.setSubject(updatedData.getSubject());
		return repository.save(existing);
	}

	// Delete a record by ID
	public String deleteData(Long id) {
		ContactUs existing = getById(id); // Ensure the record exists
		repository.delete(existing);
		return "Record with ID " + id + " has been deleted.";
	}

	// Method to save contact data
	public ContactUs addContactUs(ContactUs contactUs) {
		return repository.save(contactUs); // Save the contact data to the database
	}

	
}
