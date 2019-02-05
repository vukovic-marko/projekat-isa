package isa.projekat.controller.UserProfileControllers;


import isa.projekat.model.Forms.RegisteredUserFormData;
import isa.projekat.model.User;
import isa.projekat.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/registeredusers")
public class UserProfileController {

    @Autowired
    private RegisteredUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    private ObjectError error;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {

        //getAllUsers

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    /**
     * @return username svakog od prijatelja
     */
    @GetMapping(value = "/{username}/friends")
    public ResponseEntity<List<String>> getFriends(@PathVariable("username") String username) {

        ArrayList<String> list = new ArrayList<String>();
        list.add("1)abc");
        list.add("2)123");
        list.add("3)def");

        ResponseEntity<List<String>> listResponseEntity = new ResponseEntity<List<String>>(list, HttpStatus.OK);

        return listResponseEntity;
    }

    @GetMapping(value = "/{username}/friends/isFriend/{friendUsername}")
    public ResponseEntity<Boolean> isFriend(@PathVariable("username") String username, @PathVariable("friendsUsername") String friendsUsername) {

        ResponseEntity<Boolean> booleanResponseEntity = new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);

        return booleanResponseEntity;
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getProfileInfo(@PathVariable("username") String username) {

        // authenticate username with token
        // get user from data source
        // populate map with entries

        return new ResponseEntity<>(new HashMap<String, String>(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{username}/edit",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateProfileInfo(@PathVariable("username") String username,
                                                    @ModelAttribute("user") @Valid RegisteredUserFormData user,
                                                    BindingResult bindingResult, HttpServletRequest request) {

        ArrayList<String> messages = new ArrayList<>();
        if (!bindingResult.hasErrors()) {

            boolean success = userService.changeUserInfo(user, request);

            if (success) {

                messages.add("Uspješno izmjenjen korisnik " + username);
                return new ResponseEntity<Object>(messages, HttpStatus.OK);
            } else {

                messages.add("Neodgovarajuće korisničko ime!");
                return new ResponseEntity<Object>(messages, HttpStatus.BAD_REQUEST);
            }
        } else {

            List<ObjectError> listaGresaka = bindingResult.getAllErrors();

            for (ObjectError error : listaGresaka) {

                String msg = error.getDefaultMessage();
                messages.add(msg);
            }

            return new ResponseEntity<Object>(messages, HttpStatus.BAD_REQUEST);
        }
    }
}
