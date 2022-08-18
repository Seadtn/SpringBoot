package com.example.demo.controller;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
@Service
public class EmailValidator implements Predicate<String>{
   
	@Override
	public boolean test(String t) {
		return true;
	}

}
