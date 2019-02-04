package isa.projekat.model.Forms;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class RegisteredUserFormData {

    @NotEmpty(message = "Polje 'Korisničko ime' ne može biti prazno!")
    private String username;

    @NotEmpty(message = "Polje 'Email' ne može biti prazno!")
    private String email;

    @NotEmpty(message = "Polje 'Ime' ne može biti prazno!")
    private String firstName;

    @NotEmpty(message = "Polje 'Prezime' ne može biti prazno!")
    private String lastName;

    @NotEmpty(message = "Polje 'Grad' ne može biti prazno!")
    private String city;

    @Pattern(regexp = "[0-9]{3,3}/[0-9]{3,3}-[0-9]{2,2}-[0-9]{2,2}", message = "Neadekvatan format Telefon")
    @NotEmpty(message = "Polje 'Telefon' ne može biti prazno!")
    private String phone;

    public RegisteredUserFormData() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
