package isa.projekat.model.Forms;

import isa.projekat.model.AirlineCompany.AirlineCompany;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.util.List;

public class AirportAdminFormData extends RegisteredUserFormData {

    private List<AirlineCompany> airlineCompanies;

    public List<AirlineCompany> getAirlineCompanies() {
        return airlineCompanies;
    }

    public void setAirlineCompanies(List<AirlineCompany> airlineCompanies) {
        this.airlineCompanies = airlineCompanies;
    }
}
