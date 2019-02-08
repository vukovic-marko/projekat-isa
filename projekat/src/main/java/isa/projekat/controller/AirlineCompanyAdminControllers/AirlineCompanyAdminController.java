package isa.projekat.controller.AirlineCompanyAdminControllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/airportadmins/airlinecompanies")
@PreAuthorize("hasRole('ROLE_AIRPORT_ADMIN')")
public class AirlineCompanyAdminController {
//
//    // Destinations
//    @GetMapping(
//            value = "/destinations",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> getAirlineCompanyDestinations(@PathVariable String airlineCompanyId) {
//
//    }
//
//    @PostMapping(
//            value = "/destinations",
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> addDestination(@ModelAttribute @Valid Destination destination, @PathVariable String airlineCompanyId) {
//
//    }
//
//    // Flights
//    @GetMapping(
//            value = "/flights",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> getAirlineCompanyFlights(@ModelAttribute @Valid Flight flight, @PathVariable String airlineCompanyId) {
//
//    }
//
//    @PostMapping(
//            value = "/flights",
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> addAirlineCompanyFlight(@ModelAttribute @Valid Flight flight, @PathVariable String airlineCompanyId) {
//
//    }
//
//    // Airplanes
//    @GetMapping(
//            value = "/airplanes",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> getAirlineCompanyAirplanes(@PathVariable String airlineCompanyId) {
//
//    }
//
//    @PostMapping(
//            value = "/airplanes",
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> addFlight(@ModelAttribute @Valid Flight flight, @PathVariable String airlineCompanyId) {
//
//    }

}
