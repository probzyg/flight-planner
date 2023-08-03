package io.codelex.flightplanner.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SearchFlightRequest {
    @NotBlank
    private String from;
    @NotBlank
    private String to;
    @NotBlank
    private String departureDate;

    public SearchFlightRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }
}
