package isa.projekat.model.AirlineCompany;

import isa.projekat.model.AirlineCompany.Airplane.SittingConfiguration.Seat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private Boolean sold;

    @OneToOne
    private Seat seat;

    @ManyToOne
    private FlightReservation flightReservation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public FlightReservation getFlightReservation() {
        return flightReservation;
    }

    public void setFlightReservation(FlightReservation flightReservation) {
        this.flightReservation = flightReservation;
    }
}
