package isa.projekat.model.AirlineCompany.Airplane.SittingConfiguration;

import isa.projekat.model.AirlineCompany.Ticket;

import javax.persistence.*;

@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RowPlane rowPlane;

    @OneToOne
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RowPlane getRowPlane() {
        return rowPlane;
    }

    public void setRowPlane(RowPlane rowPlane) {
        this.rowPlane = rowPlane;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
