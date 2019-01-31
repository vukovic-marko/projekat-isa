package isa.projekat.controller.UserProfileControllers;


import isa.projekat.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user")
public class UserProfileController {

    /**
     * @return username svakog od prijatelja
     */
    @GetMapping(value = "/{username}/friends/all")
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

    @GetMapping(value = "/{username}/profileinfo")
    public ResponseEntity<Map<String, String>> getProfileInfo(@PathVariable("username") String username) {

        // authenticate username with token
        // get user from data source
        // populate map with entries

        HashMap<String,String> map = new HashMap<>();

        map.put("email", "a@a.com");
        map.put("firstName", "aname");
        map.put("lastName", "alastname");
        map.put("city", "acity");
        map.put("phone", "123/12-12-12");

        ResponseEntity<Map<String, String>> mapResponseEntity = new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);

        return mapResponseEntity;
    }

    @PatchMapping(value = "/{username}/profileinfo")
    public ResponseEntity<Boolean> updateProfileInfo(@PathVariable("username") String username, @ModelAttribute User user) {

        // get user from data source
        // authenticate with token
        // change property values of user
        // save data

        if(user.getFirstName() != null) {

            return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
        }

        ResponseEntity<Boolean> booleanResponseEntity = new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.BAD_REQUEST);

        return booleanResponseEntity;
    }
}
