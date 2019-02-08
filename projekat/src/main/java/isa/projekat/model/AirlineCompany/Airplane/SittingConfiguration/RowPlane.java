package isa.projekat.model.AirlineCompany.Airplane.SittingConfiguration;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class RowPlane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "rowPlane")
    private Set<Seat> seatList;

    @ManyToOne
    private Segment segment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(Set<Seat> seatList) {
        this.seatList = seatList;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }
}
