package com.apipokem.apipokem.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class Lista {
    private String url;
    private int weight;
    private List<Object> abilities = new ArrayList<>();
    private List<Object> types = new ArrayList<>();
    private String image;

}
