package com.example.demo.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.impl.CloudHttp2SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient.Builder;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.params.ModifiableSolrParams;

@Configuration
public class SolrConnection {
	
	private CloudHttp2SolrClient solrClient;

	@Autowired
    private ApplicationConfig config;
	
	public CloudSolrClient SolrConnection(String collection) {
		
		
		
		final List<String> solrBaseUrls = new ArrayList<String>();
		if (config.getSolrUrl() != null) {
			solrBaseUrls.add(config.getSolrUrl());
		}
		else{
			solrBaseUrls.add("http://localhost:8983/solr");
		}
		
		final CloudSolrClient client = new CloudSolrClient.Builder(solrBaseUrls).build();
		client.setDefaultCollection(collection);
		return client;
		
	}
	
	public CloudSolrClient SolrZkConnection(List<String> zkServer,String ClusterName, String Collection) {
		
//		sc = new CloudHttp2SolrClient.Builder().withZkhost(zkserver).withZkChroot(zkChrrot).build();
//		solr
		
		
		final List<String> zkServers = new ArrayList<String>();
		CloudSolrClient client = null;
		String zkChrrot = ClusterName;
		if (zkServer != null) {
			zkServers.addAll(zkServer);
		}
		else{
			zkServers.add("http://localhost:9983");
		}
		
		if(ClusterName !=null) {
			client = new CloudSolrClient.Builder(zkServers, Optional.of(zkChrrot)).build();
		}
		else {
			client = new CloudSolrClient.Builder(zkServers, Optional.empty()).build();
			   
		}

		return client;		 
		
	}
	
public Http2SolrClient SolrQueryConnection(String collection) {
		
	String serverBaseUrl = "http://localhost:8983/solr";
	Http2SolrClient solrQueryClient = new Http2SolrClient.Builder(serverBaseUrl).build();
	
	return solrQueryClient;
		
	}

}
