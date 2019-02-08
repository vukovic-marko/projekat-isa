package isa.projekat.model.AirlineCompany.Flight;


import isa.projekat.model.Destination;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class RouteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Destination destination;

    @Column
    @NotNull
    private Integer routeNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Integer getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(Integer routeNumber) {
        this.routeNumber = routeNumber;
    }
}
