
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dao.FashionData;
import com.example.demo.service.FashionDataService;

import java.util.List;

@RestController
@RequestMapping("/api/fashion")
public class FashionDataController {

    @Autowired
    private FashionDataService fashionDataService;

    @GetMapping
    public List<FashionData> getAllFashionData() {
        return fashionDataService.getAllFashionData();
    }
}
