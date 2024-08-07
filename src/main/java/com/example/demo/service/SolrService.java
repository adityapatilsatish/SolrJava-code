package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudHttp2SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.apache.solr.common.SolrInputDocument;

import com.example.demo.conf.SolrConnection;
import com.example.demo.dao.FashionData;
import com.example.demo.dao.Product;

@Service
public class SolrService {
	
	@Autowired
	public SolrConnection solrConn;
	
	 @Autowired
	    private FashionDataService fashionDataService;
	 

	 public void solrIndexData() throws SolrServerException, IOException {
		CloudSolrClient solrconnection = solrConn.SolrConnection();
		
		Product product = new Product();
		
		product.setId(1);
		product.setName("Aditya");
		
		 SolrInputDocument solrInpDocument = new SolrInputDocument();
		 solrInpDocument.addField("id", 01);
		 solrInpDocument.addField("name", "Aditya");
		
		 solrconnection.add(solrInpDocument);
		 solrconnection.commit("ecommerce");
		 solrconnection.close();
		
	}
	
	public void indexPostgressData() throws SolrServerException, IOException {
		CloudSolrClient solrconnection = solrConn.SolrConnection();
		
		List<FashionData> DataList= fashionDataService.getAllFashionData();
		Collection<SolrInputDocument> solrDocs = new ArrayList<SolrInputDocument>();

		DataList.parallelStream().forEach(fashionData -> {
			process(fashionData,solrDocs);
        });
		
		
		solrconnection.add(solrDocs);
		solrconnection.commit("ecommerce");
		solrconnection.close();
	}

	private Collection<SolrInputDocument> process(FashionData fashionData, Collection<SolrInputDocument> solrDocs) {
		SolrInputDocument solrInpDocument = new SolrInputDocument();
		solrInpDocument.addField("brand", fashionData.getBrand());
		solrInpDocument.addField("id", fashionData.getId());
		solrInpDocument.addField("pId", fashionData.getP_id());
		solrInpDocument.addField("pName", fashionData.getP_name());
		solrInpDocument.addField("img", fashionData.getImg());
		solrInpDocument.addField("ratingcount", fashionData.getRatingcount());
		solrInpDocument.addField("avgRating", fashionData.getAvg_rating());
		solrInpDocument.addField("description", fashionData.getDescription());
		solrInpDocument.addField("price", fashionData.getPrice());
		solrInpDocument.addField("pAttributes", fashionData.getP_attributes());
		solrInpDocument.addField("rating_count", fashionData.getRating_count());
		
		Random random = new Random();
//		boolean randomBoolean = random.nextBoolean();
		int randomInt = random.nextInt(10); //for getting more yes we are using random int and not random bool
		
		solrInpDocument.addField("Active", (randomInt < 7) ? "YES" : "NO");
		
		solrDocs.add(solrInpDocument);
		
		return solrDocs;
	}    
}
