package com.springboot.banking_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.banking_system.dto.ResponseMessageDto;
import com.springboot.banking_system.exception.ResourceNotFoundException;
import com.springboot.banking_system.model.Account;
import com.springboot.banking_system.model.Customer;
import com.springboot.banking_system.service.AccountService;
import com.springboot.banking_system.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/customer/register")
	public Customer registerCustomer(@RequestBody Customer customer) {
		// its used to insert the customer details i.e. to register customer
		
		return customerService.insert(customer);	
	}
	
	@PutMapping("/customer/update/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable int id,@RequestBody Customer newCustomer ,ResponseMessageDto dto) {
		
		// validate id
		try {
			Customer existingCustomerDb = customerService.validate(id);
			if(newCustomer.getContactNumber()!=null)
				existingCustomerDb.setContactNumber(newCustomer.getContactNumber());
			if(newCustomer.getAddress()!=null)
				existingCustomerDb.setAddress(newCustomer.getAddress());
			if(newCustomer.getAadharNumber()!=null)
				existingCustomerDb.setAadharNumber(newCustomer.getAadharNumber());
			if(newCustomer.getEmail()!=null)
				existingCustomerDb.setEmail(newCustomer.getEmail());
			if(newCustomer.getFirstName()!=null)
				existingCustomerDb.setFirstName(newCustomer.getFirstName());
			if(newCustomer.getLastName()!=null) 
				existingCustomerDb.setLastName(newCustomer.getLastName());
			if(newCustomer.getPanNumber()!=null)
				existingCustomerDb.setPanNumber(newCustomer.getPanNumber());
			

			//re save this existing customer having new updated value 
			existingCustomerDb = customerService.insert(existingCustomerDb);
			return ResponseEntity.ok(existingCustomerDb);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
			
	}
	
	@DeleteMapping("/customer/delete/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int id,ResponseMessageDto dto) {
		
		try {
			customerService.validate(id);// to validate the id  given
			customerService.delete(id); // to delete the id given
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		dto.setMsg("Customer deleted");
		return ResponseEntity.ok(dto);
		
	}
	
	@GetMapping("/customer/detail/{id}")
	public ResponseEntity<?> getCustomerDetail(@PathVariable int id,ResponseMessageDto dto) {
		
		try {
			customerService.validate(id); // to validate id
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
		List<Customer> list =  customerService.getCustomerDetail(id);
		return ResponseEntity.ok(list);
		
		
	}
	
	
	
	// account - Ops;
	
	@PostMapping("/customer/account/add/{cid}")
	public ResponseEntity<?> addAccount(@PathVariable int cid,@RequestBody Account accountDet,Account account ,ResponseMessageDto dto) {
		
		// validate customer id first;
		Customer customer = null;
	
	try {
		customer = customerService.validate(cid);
	} catch (ResourceNotFoundException e) {
		dto.setMsg(e.getMessage());
		ResponseEntity.badRequest().body(dto);
	}
	account.setCustomer(customer);
	account.setAccountType(accountDet.getAccountType());
	account.setBalance(accountDet.getBalance());
	account.setDateCreated(LocalDate.now());
	
	account.setAadharNumber(accountDet.getAadharNumber());
	account.setPanNumber(accountDet.getPanNumber());
	
//	System.out.println(account);
	account = accountService.insert(account);
	return ResponseEntity.ok(account);
	
	
	
	
	
		
		
	}
	
	
	
	
	
	
	
	
	


	
	
	
	
	

}




























//to get customer details
//	@GetMapping("/customer/fetch")
//	public List<Customer> getAllCustomers(){
//		List<Customer> list = customerService.getAllPolicy();
//		return list;
//		
//		
//	}
//		
//		
////		
////	@PostMapping("/customer/add-account/{customerId}")
////public ResponseEntity<?> addAccount(@PathVariable int customerId,@RequestBody Account account,ResponseMessageDto dto) {
////	// validate customerId first
////	
////	Customer customer = null;
////	
////	try {
////		customerService.validate(customerId);
////	} catch (ResourceNotFoundException e) {
////		dto.setMsg(e.getMessage());
////		ResponseEntity.badRequest().body(dto);
////	}
////	account.setAccountNumber(generateAccountNumber());
////	account.setAccountType(account.getAccountType());
////	account.setBalance(account.getBalance());
////	account.setDateCreated(account.getDateCreated());
////	account.setIfscCode(account.getIfscCode());
////	account.setStatus(account.getStatus());
////	
////	account.insert()
////	
////	
////	return null;
////	}