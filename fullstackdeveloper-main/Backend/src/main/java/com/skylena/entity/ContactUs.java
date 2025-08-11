package com.skylena.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="contact_us")
public class ContactUs {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String firstname;
	String lastname;
	String country;
	String subject;

}
