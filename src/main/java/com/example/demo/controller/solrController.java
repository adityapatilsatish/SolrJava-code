
package com.example.demo.controller;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dao.FashionData;
import com.example.demo.service.FashionDataService;
import com.example.demo.service.SolrService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class solrController {

    @Autowired
    private SolrService solrService;

    @RequestMapping("/solr")
    public void indexSolrRec() throws SolrServerException, IOException {
        solrService.solrIndexData();
    }

    @RequestMapping("/solrFashiondata")
    public void indexFashionToSolr() throws SolrServerException, IOException {
        solrService.indexPostgressData();
    }
}
