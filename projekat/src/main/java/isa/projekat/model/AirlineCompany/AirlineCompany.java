package isa.projekat.model.AirlineCompany;

import isa.projekat.model.AirlineCompany.Airplane.Airplane;
import isa.projekat.model.AirlineCompany.Flight.Flight;
import isa.projekat.model.Destination;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class AirlineCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column
    private Set<Destination> destinations;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "airlineCompany")
    @Column
    private Set<Airplane> airplanes;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String address;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(Set<Destination> destinations) {
        this.destinations = destinations;
    }

    public Set<Airplane> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(Set<Airplane> airplanes) {
        this.airplanes = airplanes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
