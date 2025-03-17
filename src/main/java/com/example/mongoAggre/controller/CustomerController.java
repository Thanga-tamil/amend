package com.example.mongoAggre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongoAggre.dto.CustomerReqDto;
import com.example.mongoAggre.model.Customer;
import com.example.mongoAggre.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/buy-product")
	public String addToCart(@RequestBody CustomerReqDto customerReqDto ) {

		return customerService.addToCart(customerReqDto);
	}

	@GetMapping("/getData")
	public List<Customer> getData(@RequestHeader String name) {
		return customerService.getData(name);
	}
}
