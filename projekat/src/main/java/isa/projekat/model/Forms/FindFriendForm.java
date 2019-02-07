package isa.projekat.model.Forms;

import org.hibernate.validator.constraints.Email;

public class FindFriendForm {

    private String firstName;
    private String email;
    private String lastName;

    public FindFriendForm() {

        firstName = "";
        lastName = "";
        email = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
