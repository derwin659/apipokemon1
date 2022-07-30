package com.apipokem.apipokem.model;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Pokemones {
    private List<Results> results = new ArrayList<>();
}
