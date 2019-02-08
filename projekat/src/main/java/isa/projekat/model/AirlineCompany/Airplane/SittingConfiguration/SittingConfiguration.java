package isa.projekat.model.AirlineCompany.Airplane.SittingConfiguration;

import isa.projekat.model.AirlineCompany.Airplane.Airplane;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class SittingConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "sittingConfiguration")
    private Set<Segment> segmentList;

    @ManyToOne
    private Airplane airplane;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Segment> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(Set<Segment> segmentList) {
        this.segmentList = segmentList;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }
}
