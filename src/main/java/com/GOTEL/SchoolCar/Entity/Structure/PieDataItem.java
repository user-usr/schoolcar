package com.GOTEL.SchoolCar.Entity.Structure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PieDataItem {
    private String name;
    private int value;

    public PieDataItem(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
