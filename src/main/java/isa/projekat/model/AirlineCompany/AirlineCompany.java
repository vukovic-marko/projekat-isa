package isa.projekat.model.AirlineCompany;

import isa.projekat.model.Destination;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AirlineCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "airlineCompany", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column
    private Set<Flight> flightSet;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column
    private Set<Destination> destinations;
}
