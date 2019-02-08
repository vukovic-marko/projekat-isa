package isa.projekat.controller.AirlineCompanyAdminControllers;


import isa.projekat.model.AirlineCompany.AirlineCompany;
import isa.projekat.model.AirlineCompany.Airplane.Airplane;
import isa.projekat.model.Forms.AirportAdminFormData;
import isa.projekat.model.Forms.FindFriendForm;
import isa.projekat.model.Forms.NewFlightForm;
import isa.projekat.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/airportadmins")
@PreAuthorize("hasRole('ROLE_AIRPORT_ADMIN')")
public class AirlineCompanyAdminViewController {

    @ModelAttribute
    public void currentUser(Model model) {

        // add current user info to data
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        AirportAdminFormData airportAdminFormData = new AirportAdminFormData();
        BeanUtils.copyProperties(currentUser, airportAdminFormData);

        model.addAttribute("user", airportAdminFormData);
    }

    @GetMapping("/profile")
    public String getProfile(Model model, HttpServletRequest request) {

        return "user/fragments/profileInfo :: profileInfoForm";
    }

    @GetMapping("/airlinecompanies")
    public String getAirlineCompanies(Model model, HttpServletRequest request) {

        return "airportadmin/profile :: airlinecompanies";
    }

    @GetMapping("/flights")
    public String getFlights(Model model, HttpServletRequest request) {

        // airlineCompaniesModel, sve aviokompanije kojima upravlja korisnik
        ArrayList<AirlineCompany> airlineCompanies = new ArrayList<>();
        airlineCompanies.add(new AirlineCompany());
        airlineCompanies.add(new AirlineCompany());
        airlineCompanies.add(new AirlineCompany());

        model.addAttribute("airlineCompaniesModel", airlineCompanies);

        return "airportadmin/fragments/flights :: flights";
    }

    @GetMapping("/flights/search")
    public String getSearchForm(Model model, HttpServletRequest request) {

        model.addAttribute("searchFlightsFormData", new NewFlightForm());

        return "airportadmin/fragments/flights :: searchFlights";
    }

    @GetMapping("/flights/new")
    public String getNewFlight(Model model, HttpServletRequest request) {

        model.addAttribute("newFlightFormData", new NewFlightForm());

        return "airportadmin/fragments/flights :: newFlight";
    }


    @GetMapping("/flights/queryResults")
    public String getFlightFilterResult(
            @ModelAttribute("searchFlightsFormData") @Valid NewFlightForm flightFormQuery,
            Model model,
            HttpServletRequest request) {

        return "airportadmin/fragments/flights :: flightsresults";
    }
}
