package com.javatab.springelasticdemo.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    private String id;

    private String name;

    private List<Genre> genre;

    private Double rating;

    private Director director;

}
