package com.javatab.springelasticdemo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatab.springelasticdemo.model.Movie;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MovieService {

    private RestHighLevelClient client;

    private ObjectMapper objectMapper;

    public MovieService(RestHighLevelClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public String createMovieDocument(Movie movie) throws IOException {
        UUID uuid = UUID.randomUUID();
        movie.setId(uuid.toString());

        Map documentMapper = objectMapper.convertValue(movie, Map.class);

        IndexRequest indexRequest = new IndexRequest("movie-document").id(movie.getId())
                .source(documentMapper);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

        return indexResponse
                .getResult()
                .name();
    }

    public Movie findById(String id) throws IOException {
        GetRequest getRequest = new GetRequest("movie-document", id);

        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

        Map<String, Object> resultMap = getResponse.getSource();

        return objectMapper
                .convertValue(resultMap, Movie.class);


    }

    public String updateMovie(Movie movie) throws IOException {

        Movie resultDocument = findById(movie.getId());

        UpdateRequest updateRequest = new UpdateRequest(
                "movie-document",
                resultDocument.getId());

        Map documentMapper =
                objectMapper.convertValue(movie, Map.class);

        updateRequest.doc(documentMapper);

        UpdateResponse updateResponse =
                client.update(updateRequest, RequestOptions.DEFAULT);

        return updateResponse
                .getResult()
                .name();
    }

    public List<Movie> findAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);
    }

    public List<Movie> searchByGenre(String genre) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("genre.name", genre);
        searchSourceBuilder.query(matchQueryBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse response =
                client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(response);
    }

    private List<Movie> getSearchResult(SearchResponse response) {

        SearchHit[] searchHit = response.getHits().getHits();

        List<Movie> movies = new ArrayList<>();

        for (SearchHit hit : searchHit){
            movies
                    .add(objectMapper
                            .convertValue(hit
                                    .getSourceAsMap(), Movie.class));
        }

        return movies;
    }
}
