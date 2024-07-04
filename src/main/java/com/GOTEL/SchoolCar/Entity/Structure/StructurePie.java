package com.GOTEL.SchoolCar.Entity.Structure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class StructurePie {
    private String title;
    private int sumNum;
    private List<PieDataItem> structure;
}
