package io.codelex.flightplanner.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.domain.Airport;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    private LocalDateTime arrivalTime;

     public AddFlightRequest() {
    }
}
