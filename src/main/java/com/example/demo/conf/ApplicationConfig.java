package com.example.demo.conf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

    @Value("${solr.url}")
    private String solrUrl;

    public String getSolrUrl() {
        return solrUrl;
    }

    @Value("${zk.string}")
    private String zkString;

    public String getZkSrting() {
        return zkString;
    }
}
