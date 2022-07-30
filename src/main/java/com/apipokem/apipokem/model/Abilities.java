package com.apipokem.apipokem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Abilities {
    private List<Ability> abilitySet= new ArrayList<>();
    private Boolean is_hidden;
    private int slot;
}
