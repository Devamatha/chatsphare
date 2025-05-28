package com.kanzariya.chatsphere.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table( uniqueConstraints = { @UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "mobileNumber") })
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_Id;
	private String fullName;
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	@Column(nullable = false)
	private String email;
	@NotBlank(message = "Mobile number is required")
	@Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
	@Column(nullable = false)
	private String mobileNumber;
	private String role;
	//@JsonIgnore
	private String password;
}
