package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirportDatabaseRepository extends JpaRepository<Airport, String> {
    @Query("SELECT a FROM Airport a WHERE lower(a.airport) LIKE %:phrase% OR lower(a.city) LIKE %:phrase% OR lower(a.country) LIKE %:phrase%")
    List<Airport> searchAirportsByPhrase(String phrase);
}
