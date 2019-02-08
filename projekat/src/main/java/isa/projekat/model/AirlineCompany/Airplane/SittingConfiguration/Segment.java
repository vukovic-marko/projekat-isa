package isa.projekat.model.AirlineCompany.Airplane.SittingConfiguration;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Segment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "segment")
    private Set<RowPlane> rowPlaneList;

    @ManyToOne
    private SittingConfiguration sittingConfiguration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<RowPlane> getRowPlaneList() {
        return rowPlaneList;
    }

    public void setRowPlaneList(Set<RowPlane> rowPlaneList) {
        this.rowPlaneList = rowPlaneList;
    }

    public SittingConfiguration getSittingConfiguration() {
        return sittingConfiguration;
    }

    public void setSittingConfiguration(SittingConfiguration sittingConfiguration) {
        this.sittingConfiguration = sittingConfiguration;
    }
}
