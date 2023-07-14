package io.codelex.flightplanner;

import io.codelex.flightplanner.controller.AdminController;
import io.codelex.flightplanner.controller.TestingController;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.repository.FlightPlannerRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.service.FlightPlannerService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlightPlannerApplicationTests {

    @Autowired
    FlightPlannerRepository flightPlannerRepository;
    @Autowired
    FlightPlannerService flightPlannerService;
    @Autowired
    AdminController adminController;
    @Autowired
    TestingController testingController;

    private final Airport airport1 = new Airport("US", "Wasilla", "AK99");
    private final Airport airport2 = new Airport("US", "PAW PAW", "LL54");
    @Test
    @DisplayName("Should be able to add flights")
    void addFlights() {
        assertEquals(0, flightPlannerRepository.getFlights().size());

        AddFlightRequest flightRequest = createFlightRequest();
        adminController.addFlight(flightRequest);

        assertEquals(1, flightPlannerRepository.getFlights().size());
    }

    AddFlightRequest createFlightRequest() {
        AddFlightRequest flightRequest = new AddFlightRequest();
        flightRequest.setFrom(airport1);
        flightRequest.setTo(airport2);
        flightRequest.setCarrier("WESTERN PACIFIC AIRLINES");
        flightRequest.setDepartureTime("2023-03-16 22:47");
        flightRequest.setArrivalTime("2023-03-17 14:47");
        return flightRequest;
    }

    @Test
    @DisplayName("Should be able to clear flights")
    void clearFlights() {
        AddFlightRequest flightRequest = createFlightRequest();
        adminController.addFlight(flightRequest);

        assertEquals(1, flightPlannerRepository.getFlights().size());

        testingController.clearFlights();

        assertEquals(0, flightPlannerRepository.getFlights().size());
    }

}
