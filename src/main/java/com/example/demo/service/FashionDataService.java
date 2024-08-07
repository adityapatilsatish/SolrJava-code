package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.FashionData;
import com.example.demo.repository.FashionDataRepository;

@Service
public class FashionDataService {


    @Autowired
    private FashionDataRepository repository;
    
	public List<FashionData> getAllFashionData() {
        return repository.findAll();
    }
}
