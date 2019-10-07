package com.javatab.springelasticdemo.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  /*  @Value("${elasticsearch.host:localhost}")
    public String host;
    @Value("${elasticsearch.port:9300}")
    public int port;*/

    /*public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }*/


    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));
    }
}
