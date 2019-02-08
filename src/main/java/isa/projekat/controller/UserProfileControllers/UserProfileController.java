package isa.projekat.controller.UserProfileControllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import isa.projekat.model.Forms.FindFriendForm;
import isa.projekat.model.Forms.RegisteredUserFormData;
import isa.projekat.model.User;
import isa.projekat.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
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

    @PostMapping(
            value = "/friends/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addFriend(@RequestBody Map<String,String> payload, HttpServletRequest request) {

        Long idPrijatelja = Long.parseLong(payload.get("id"));

        boolean success = userService.sendRequestToRegisteredUser(idPrijatelja, request);

        String ret;
        if(success) {

            ret = "Uspjesno poslan zahtjev!";
            return new ResponseEntity<String>(ret, HttpStatus.OK);
        } else {

            ret = "Nespjesno poslan zahtjev!";
            return new ResponseEntity<String>(ret, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(
            value = "/friends",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getFriends(HttpServletRequest request) {

        ObjectMapper om = new ObjectMapper();

        List<User> friends = userService.getFriendsOfCurrentUser(request);

        return new ResponseEntity<List<User>>(friends, HttpStatus.OK);
    }

    @PatchMapping(
            value = "/edit",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateProfileInfo(
                                                    @ModelAttribute("user") @Valid RegisteredUserFormData user,
                                                    BindingResult bindingResult, HttpServletRequest request) {

        ArrayList<String> messages = new ArrayList<>();
        if (!bindingResult.hasErrors()) {

            boolean success = userService.changeUserInfo(user, request);

            if (success) {

                messages.add("Uspješno izmjenjen korisnik " + userService.getCurrentUser(request).getUsername());
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

    @DeleteMapping(value = "/requests/cancelRequest",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> cancelRequest(@RequestBody Map<String,String> payload, HttpServletRequest request) {

        String strIdRec = payload.get("idReceiver");
        Long idReceiver = Long.parseLong(strIdRec);

        userService.cancelRequest(idReceiver, request);

        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @PatchMapping(value = "/requests/acceptRequest",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> acceptRequest(@RequestBody Map<String,String> payload, HttpServletRequest request) {

        String strIdReq = payload.get("idRequester");
        Long idRequester = Long.parseLong(strIdReq);

        userService.acceptRequest(idRequester, request);

        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @DeleteMapping(value = "/friends/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteFriend(@RequestBody Map<String,String> payload, HttpServletRequest request) {

        String strIdFriend = payload.get("idFriend");
        Long idFriend = Long.parseLong(strIdFriend);

        userService.deleteFriend(idFriend, request);

        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
