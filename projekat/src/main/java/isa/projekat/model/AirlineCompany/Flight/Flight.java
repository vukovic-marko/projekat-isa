package isa.projekat.model.AirlineCompany.Flight;

import isa.projekat.model.AirlineCompany.AirlineCompany;
import isa.projekat.model.AirlineCompany.Airplane.Airplane;
import isa.projekat.model.AirlineCompany.FlightReservation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Airplane airplane;

    @OneToMany
    @Size(min = 2)
    private Set<RouteStop> routeStop;

    @OneToMany(mappedBy = "flight")
    private Set<FlightReservation> flightReservations;

    @Column
    @NotNull
    private Date startDate;

    @Column
    @NotNull
    private Date endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<RouteStop> getRouteStop() {
        return routeStop;
    }

    public void setRouteStop(Set<RouteStop> routeStop) {
        this.routeStop = routeStop;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }
}
