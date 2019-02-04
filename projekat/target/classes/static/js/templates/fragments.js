$(document).ready(function () {

    // Resetovanje formi za logovanje nakon zatvaranja
    $(document).on("hidden.bs.modal", "#loginModal", function (event) {

        $("#loginForm").trigger("reset");
    });
    $(document).on("hidden.bs.modal", "#registerModal", function (event) {

        $("#registerForm").trigger("reset");
    });

    // Validacija login i register formi
    $.validator.methods.phoneCheck = function (value, element) {
        return this.optional(element)
            || /([0-9]{3,3}\/[0-9]{3,3}-[0-9]{2,2}-[0-9]{2,2})$/
                .test(value);
    };

    $('#loginForm').validate({
        rules: {
            username: "required",
            password: "required"
        },
        messages: {
            username: "Ime nije uneto",
            password: "Lozinka nije uneta"
        }
    });

    $('#registerForm').validate({
        rules: {
            username: {
                required: true,
                remote: "user/checkusername/"
            },
            password: "required",
            email: {
                required: true,
                email: true,
                remote: "user/checkemail/"
            },
            firstName: {
                required: true
            },
            lastName: {
                required: true
            },
            password2: {
                required: true,
                equalTo: "#passwordRegister"
            },
            phone: {
                required: true,
                phoneCheck: true
            },
            city: {
                required: true
            }

        },
        messages: {
            username: {
                required: "Ime nije uneto",
                remote: "Korisničko ime je zauzeto"
            },

            password: "Lozinka nije uneta",

            password2: {
                required: "Unesite ponovo lozinku",
                equalTo: "Lozinke se ne poklapaju"
            },
            firstName: {
                required: "Ime nije uneto",
                letters: "Dozvoljena su samo slova srpske latinice i razmaci"
            },
            lastName: {
                required: "Prezime nije uneto",
                letters: "Dozvoljena su samo slova srpske latinice i razmaci"
            },
            email: {
                required: "Email adresa nije uneta",
                email: "Neispravan format adrese",
                remote: "Već postoji nalog koji je registrovan na ovu email adresu"
            },
            phone: {
                required: "Telefon nije unet",
                phoneCheck: "Neispravan format (123/456-78-99)"
            },
            city: {
                required: "Grad nije unet"
            }
        }
    });

});