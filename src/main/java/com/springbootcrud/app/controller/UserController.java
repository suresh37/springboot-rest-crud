package com.springbootcrud.app.controller;

import java.util.List;

import javax.naming.directory.AttributeInUseException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootcrud.app.exception.ResourceNotFoundException;
import com.springbootcrud.app.model.User;
import com.springbootcrud.app.repository.UserRepository;

@RequestMapping("/api/users")
@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable Integer id) {
		return this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with this id"));
	}

	@PostMapping(value = "")
	public User createUser(@Valid @RequestBody User user) {
		return this.userRepository.save(user);
	}

	@PutMapping(value = "/{id}")
	public User editUser(@Valid @RequestBody User user, @PathVariable Integer id) {
		User existingUser = this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with this id"));
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		System.out.println("Updated By: "+user.getUpdatedBy());
		existingUser.setUpdatedBy(user.getUpdatedBy());
		existingUser.setUpdatedDate(user.getUpdatedDate());
		return this.userRepository.save(existingUser);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<User> deleetUser(@PathVariable Integer id) {
		User user = this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with this id"));
		this.userRepository.delete(user);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

}
