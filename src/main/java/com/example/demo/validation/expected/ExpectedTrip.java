package com.example.demo.validation.expected;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Object holding information of an expected trip.
 * <p>
 * The fields that form an expected trip are:
 * <ul>
 *     <li>a range of the standard price of the trip</li>
 *     <li>a list of {@link ExpectedFlight} objects</li>
 */
@Builder
@Data
public class ExpectedTrip {

    private List<ExpectedFlight> expectedFlightList;
    private Float priceMin;
    private Float priceMax;
}
