package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repository.FlightPlannerInMemoryRepository;
import io.codelex.flightplanner.request.SearchFlightRequest;
import io.codelex.flightplanner.response.PageResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FlightPlannerInMemoryServiceTest {

    @Mock
    FlightPlannerInMemoryRepository flightPlannerInMemoryRepository;

    @InjectMocks
    FlightPlannerInMemoryService flightPlannerInMemoryService;

    private final String from = "LL54";
    private final String to = "AK99";
    private final String departureDate = "2023-03-17";
    private final SearchFlightRequest request = new SearchFlightRequest(from, to, departureDate);

    @Test
    @DisplayName("Should find flight")
    void searchFlight() {
        List<Flight> mockList = createMockFlightsList();
        Mockito.when(flightPlannerInMemoryRepository.getFlights()).thenReturn(mockList);

        PageResult<Flight> result = flightPlannerInMemoryService.searchFlight(request);
        Mockito.verify(flightPlannerInMemoryRepository).getFlights();

        assertEquals(result.getTotalItems(), 1);
    }

    private List<Flight> createMockFlightsList() {
        Airport airport1 = new Airport("US", "Wasilla", "AK99");
        Airport airport2 = new Airport("US", "PAW PAW", "LL54");
        Flight flight1 = new Flight(airport1, airport2, "WESTERN PACIFIC AIRLINES", "2023-03-16 22:47", "2023-03-17 14:47");
        Flight flight2 = new Flight(airport2, airport1, "WESTERN PACIFIC AIRLINES", "2023-03-17 17:00", "2023-03-18 9:00");
        Flight flight3 = new Flight(airport1, airport2, "WESTERN PACIFIC AIRLINES", "2023-03-18 12:00", "2023-03-19 4:00");

        List<Flight> mockList = new ArrayList<>();
        mockList.add(flight1);
        mockList.add(flight2);
        mockList.add(flight3);

        return mockList;
    }
}