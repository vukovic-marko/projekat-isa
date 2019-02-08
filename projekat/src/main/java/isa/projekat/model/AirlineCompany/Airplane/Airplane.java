package isa.projekat.model.AirlineCompany.Airplane;

import isa.projekat.model.AirlineCompany.AirlineCompany;
import isa.projekat.model.AirlineCompany.Airplane.SittingConfiguration.SittingConfiguration;
import isa.projekat.model.AirlineCompany.Flight.Flight;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "airplane")
    private Set<SittingConfiguration> sittingConfiguration;

    @ManyToOne
    private AirlineCompany airlineCompany;

    @OneToMany(mappedBy = "airplane")
    private Set<Flight> flights;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<SittingConfiguration> getSittingConfiguration() {
        return sittingConfiguration;
    }

    public void setSittingConfiguration(Set<SittingConfiguration> sittingConfiguration) {
        this.sittingConfiguration = sittingConfiguration;
    }

    public AirlineCompany getAirlineCompany() {
        return airlineCompany;
    }

    public void setAirlineCompany(AirlineCompany airlineCompany) {
        this.airlineCompany = airlineCompany;
    }
}
