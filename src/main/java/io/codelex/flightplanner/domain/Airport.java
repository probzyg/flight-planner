package io.codelex.flightplanner.domain;

public class Airport {
    private final String country;
    private final String city;
    private final String airportName;

    public Airport(String country, String city, String airportName) {
        this.country = country;
        this.city = city;
        this.airportName = airportName;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAirportName() {
        return airportName;
    }
}
