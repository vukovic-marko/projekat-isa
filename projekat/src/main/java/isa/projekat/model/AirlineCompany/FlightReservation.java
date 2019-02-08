package isa.projekat.model.AirlineCompany;

import isa.projekat.model.AirlineCompany.Flight.Flight;

import javax.persistence.*;
import java.util.Set;

@Entity
public class FlightReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "flightReservation")
    private Set<Ticket> tickets;

    @ManyToOne
    private Flight flight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
