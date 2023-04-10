package com.example.demo.validation.actual;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Object holding information of the actual trip found.
 * <p>
 * The fields retrieved are
 * <ul>
 *     <li>the standard price of the trip</li>
 *     <li>a list of {@link ActualFlight} objects</li>
 */
@Builder
@Data
public class ActualTrip {

    private Float standardPrice;
    private List<ActualFlight> actualFlightList;
}
