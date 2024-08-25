package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.conf.SolrConnection;
import com.example.demo.dao.FashionData;
import com.example.demo.dao.Product;

@Service
public class SolrIndexService {

    @Autowired
    private SolrConnection solrConn;

    @Autowired
    private FashionDataService fashionDataService;

    private static final String DEFAULT_COLLECTION = "ecommerce";

    public void solrIndexData() throws SolrServerException, IOException {
        CloudSolrClient solrconnection = solrConn.SolrConnection(DEFAULT_COLLECTION);

        Product product = new Product();
        product.setId(1);
        product.setName("Aditya");

        SolrInputDocument solrInpDocument = new SolrInputDocument();
        solrInpDocument.addField("id", 01);
        solrInpDocument.addField("name", "Aditya");

        solrconnection.add(solrInpDocument);
        solrconnection.commit(DEFAULT_COLLECTION);
        solrconnection.close();
    }

    public void indexPostgressData() throws SolrServerException, IOException {
        CloudSolrClient solrconnection = solrConn.SolrConnection(DEFAULT_COLLECTION);

        List<FashionData> dataList = fashionDataService.getAllFashionData();
//        Collection<SolrInputDocument> solrDocs = new ArrayList<>();
//
//        dataList.parallelStream().forEach(fashionData -> {
//            process(fashionData, solrDocs);
//        });

        Collection<SolrInputDocument> solrDocs = dataList.parallelStream()
        	    .map(this::process)
        	    .filter(Objects::nonNull)
        	    .collect(Collectors.toList());
        
        solrconnection.add(solrDocs);
        solrconnection.commit(DEFAULT_COLLECTION);
        solrconnection.close();
    }
    
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void indexPostgressDataBatch() throws SolrServerException, IOException {
        CloudSolrClient solrconnection = solrConn.SolrConnection(DEFAULT_COLLECTION);
        List<FashionData> dataList = fashionDataService.getAllFashionData();
        final int batchSize = 1000;
        final Collection<SolrInputDocument> solrDocs = Collections.synchronizedList(new ArrayList<>());

        List<Future<Void>> futures = new ArrayList<>();

        try {
            for (FashionData fashionData : dataList) {
                futures.add(executor.submit(() -> {
                    process(fashionData);
                    synchronized (solrDocs) {
                        if (solrDocs.size() >= batchSize) {
                            solrconnection.add(solrDocs);
                            solrconnection.commit();
                            solrDocs.clear();
                        }
                    }
                    return null;
                }));
            }

            // Wait for all tasks to complete
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            // Index remaining documents
            if (!solrDocs.isEmpty()) {
                solrconnection.add(solrDocs);
                solrconnection.commit();
            }
        } finally {
            solrconnection.close();
            shutdownExecutor();
        }
    }

    private void shutdownExecutor() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    // Log failure to shut down
                }
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private SolrInputDocument process(FashionData fashionData) {
        if (fashionData != null) {
            SolrInputDocument solrInpDocument = new SolrInputDocument();
            solrInpDocument.addField("id", fashionData.getId());
            solrInpDocument.addField("pId", fashionData.getP_id());
            solrInpDocument.addField("pName", fashionData.getP_name());
            solrInpDocument.addField("colour", fashionData.getColour());
            solrInpDocument.addField("brand", fashionData.getBrand());
            solrInpDocument.addField("img", fashionData.getImg());
            solrInpDocument.addField("ratingcount", fashionData.getRatingcount());
            solrInpDocument.addField("avgRating", fashionData.getAvg_rating());
            solrInpDocument.addField("description", fashionData.getDescription());
            solrInpDocument.addField("price", fashionData.getPrice());
            solrInpDocument.addField("pAttributes", fashionData.getP_attributes());
            solrInpDocument.addField("rating_count", fashionData.getRating_count());

            // Add random "Active" field
            Random random = new Random();
            int randomInt = random.nextInt(10);
            solrInpDocument.addField("Active", (randomInt < 7) ? "YES" : "NO");

            return solrInpDocument;
        }
        return null; // Return null if fashionData is null
    }

}
