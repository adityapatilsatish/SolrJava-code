
package com.example.demo.controller;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dao.FashionData;
import com.example.demo.service.FashionDataService;
import com.example.demo.service.SolrIndexService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/index")
public class solrIndexController {

    @Autowired
    private SolrIndexService solrService;

    @RequestMapping("/solr")
    public void indexSolrRec() throws SolrServerException, IOException {
        solrService.solrIndexData();
    }

    @RequestMapping("/solrFashiondata")
    public void indexFashionToSolr() throws SolrServerException, IOException {
        solrService.indexPostgressData();
    }
    
    @RequestMapping("/solrFashiondataBatch")
    public void indexFashionToSolrBatch() throws SolrServerException, IOException {
        solrService.indexPostgressDataBatch();
    }

}
