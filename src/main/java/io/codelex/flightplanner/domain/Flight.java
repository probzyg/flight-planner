package io.codelex.flightplanner.domain;

import io.codelex.flightplanner.IdGen;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "from_airport")
    private Airport from;
    @ManyToOne
    @JoinColumn(name = "to_airport")
    private Airport to;
    @Column(name = "carrier")
    private String carrier;
    @Column(name = "departure_time")
    private String departureTime;
    @Column(name = "arrival_time")
    private String arrivalTime;

    public Flight(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.id = IdGen.getNextId();
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight() {
    }
    public String getDepartureDate() {
        return getDepartureTime().split(" ")[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(from, flight.from) && Objects.equals(to, flight.to) && Objects.equals(carrier, flight.carrier) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(arrivalTime, flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, carrier, departureTime, arrivalTime);
    }
}
