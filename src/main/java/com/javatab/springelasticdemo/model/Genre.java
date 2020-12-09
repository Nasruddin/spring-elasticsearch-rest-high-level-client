package com.javatab.springelasticdemo.model;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Genre {

    @NonNull
    private String name;
}
