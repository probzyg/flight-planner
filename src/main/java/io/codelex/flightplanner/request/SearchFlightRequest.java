package io.codelex.flightplanner.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SearchFlightRequest {
    @NotBlank
    private String from;
    @NotBlank
    private String to;
    @NotNull
    private LocalDate departureDate;

    public SearchFlightRequest(String from, String to, LocalDate departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }
}
