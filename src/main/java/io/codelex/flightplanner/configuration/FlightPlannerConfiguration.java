package io.codelex.flightplanner.configuration;

import io.codelex.flightplanner.repository.AirportDatabaseRepository;
import io.codelex.flightplanner.repository.FlightDatabaseRepository;
import io.codelex.flightplanner.repository.FlightPlannerInMemoryRepository;
import io.codelex.flightplanner.service.FlightPlannerDatabaseService;
import io.codelex.flightplanner.service.FlightPlannerInMemoryService;
import io.codelex.flightplanner.service.FlightPlannerService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightPlannerConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "in-memory")
    public FlightPlannerService getInMemoryVersion(FlightPlannerInMemoryRepository flightPlannerInMemoryRepository) {
        return new FlightPlannerInMemoryService(flightPlannerInMemoryRepository);
    }

    @Bean
    @ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
    public FlightPlannerService getDatabaseVersion(FlightDatabaseRepository flightDatabaseRepository, AirportDatabaseRepository airportDatabaseRepository) {
        return new FlightPlannerDatabaseService(flightDatabaseRepository, airportDatabaseRepository);
    }
}
