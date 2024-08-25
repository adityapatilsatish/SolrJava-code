
package com.example.demo.controller;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.SolrQueryService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/query")
public class solrQueryController {

    @Autowired
    private SolrQueryService solrService;
    
    @GetMapping("/Search")
    public ResponseEntity<Map<String, Object>> QuerySolr(@RequestParam(name = "query") String query) throws SolrServerException, IOException {
        Map<String, Object> response = solrService.querySolr(query,"ecommerce",null);
        return ResponseEntity.ok(response);
    }

}
