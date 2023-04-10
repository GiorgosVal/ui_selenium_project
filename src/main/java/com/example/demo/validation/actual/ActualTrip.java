package com.example.demo.validation.actual;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ActualTrip {

    private Float standardPrice;
    private Float flexiblePrice;
    private List<ActualFlight> actualFlightList;
}
