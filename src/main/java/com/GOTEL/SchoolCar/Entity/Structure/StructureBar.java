package com.GOTEL.SchoolCar.Entity.Structure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class StructureBar {
    private String title;
    private List<String> types;
    private List<Integer> values;
}
