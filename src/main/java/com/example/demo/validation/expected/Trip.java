package com.example.demo.validation.expected;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Trip {

    // for actual and expected results
    private List<Flight> expectedFlightList;

    // for actual results
    private Float priceMin;
    private Float priceMax;
}
