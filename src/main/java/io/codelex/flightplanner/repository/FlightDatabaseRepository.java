package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightDatabaseRepository extends JpaRepository<Flight, Long> {
}
