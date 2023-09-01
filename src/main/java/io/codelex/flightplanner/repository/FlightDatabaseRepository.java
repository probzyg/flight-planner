package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FlightDatabaseRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE UPPER(f.from.airport) = UPPER(:from) AND UPPER(f.to.airport) = UPPER(:to) AND CAST(f.departureTime AS date) = CAST(:departureDate as date)")
    List<Flight> searchFlightsByFromToAndDepartureDate(@Param("from") String from,
                                                       @Param("to") String to,
                                                       @Param("departureDate") LocalDate departureDate);
}
