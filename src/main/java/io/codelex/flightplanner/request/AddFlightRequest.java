package io.codelex.flightplanner.request;


import io.codelex.flightplanner.domain.Airport;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


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

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
