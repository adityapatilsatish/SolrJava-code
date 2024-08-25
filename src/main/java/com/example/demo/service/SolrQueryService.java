package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.conf.SolrConnection;

@Service
public class SolrQueryService {

    @Autowired
    private SolrConnection solrConn;

    private String defCollection = "ecommerce";
    private String defqt = "/customreq";

    public Map<String, Object> querySolr(String query, String collection, String qt) {
        defCollection = (collection != null) ? collection : defCollection;
        defqt = (qt != null) ? qt : defqt;
        CloudSolrClient solrconnection = solrConn.SolrConnection(defCollection);

        SolrQuery sq = new SolrQuery();
        sq.setRequestHandler(defqt);
//        sq.setQuery("*" + query + "*");
        sq.setQuery(query);
        sq.setRows(10);

        Map<String, Object> result = new HashMap<>();

        try {
            QueryResponse solrResponse = solrconnection.query(defCollection, sq, METHOD.POST);

            SolrDocumentList solrResults = solrResponse.getResults();
            List<FacetField> solrFacets = solrResponse.getFacetFields();

            result.put("numFound", solrResults.getNumFound());

            List<Map<String, Object>> documents = new ArrayList<>();
            solrResults.forEach(doc -> {
                Map<String, Object> document = new HashMap<>();
                doc.getFieldNames().forEach(fieldName -> {
                    document.put(fieldName, doc.getFieldValue(fieldName));
                });
                documents.add(document);
            });
            result.put("documents", documents);

            List<Map<String, Object>> facets = new ArrayList<>();
            if (solrFacets != null) {
                for (FacetField facet : solrFacets) {
                    Map<String, Object> facetInfo = new HashMap<>();
                    facetInfo.put("name", facet.getName());

                    List<Map<String, Object>> facetValues = new ArrayList<>();
                    for (FacetField.Count count : facet.getValues()) {
                        Map<String, Object> valueInfo = new HashMap<>();
                        valueInfo.put("name", count.getName());
                        valueInfo.put("count", count.getCount());
                        facetValues.add(valueInfo);
                    }

                    facetInfo.put("values", facetValues);
                    facets.add(facetInfo);
                }
            }
            result.put("facets", facets);

        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            result.put("error", e.getMessage());
        }

        return result;
    }
}
