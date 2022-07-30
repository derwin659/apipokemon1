package com.apipokem.apipokem.model;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Lista1 extends Lista{

  private List<Object> descriptions;
  private List<Object>  evolutions;
}
