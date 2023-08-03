package io.codelex.flightplanner.request;


import io.codelex.flightplanner.domain.Airport;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddFlightRequest {
    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "from_airport")
    private Airport from;
    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "to_airport")
    private Airport to;
    @NotBlank
    private String carrier;
    @NotBlank
    private String departureTime;
    @NotBlank
    private String arrivalTime;

     public AddFlightRequest() {
    }
}
