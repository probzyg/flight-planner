package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportDatabaseRepository extends JpaRepository<Airport, String> {
}
