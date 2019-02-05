package isa.projekat.service;

import isa.projekat.model.Car;
import isa.projekat.model.Forms.FindFriendForm;
import isa.projekat.model.Forms.RegisteredUserFormData;
import isa.projekat.model.RegisteredUser.Friendship;
import isa.projekat.model.RentACarCompany;
import isa.projekat.model.User;
import isa.projekat.repository.FriendshipRepository;
import isa.projekat.security.TokenUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Service
public class RegisteredUserService {

    @Autowired
    private isa.projekat.repository.UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private TokenUtils tokenUtils;


    public User getCurrentUser(HttpServletRequest request) {

        User user;

        String token = tokenUtils.getToken(request);
        if (token == null) {

            user = null;
        } else {

            String usernameFromToken = this.tokenUtils.getUsernameFromToken(token);
            user = (User) this.userDetailsService.loadUserByUsername(usernameFromToken);
        }

        return user;
    }

    /**
     * Cuvaju se izmjenjeni podaci korisnika
     *
     * @param user podaci za izmjenu
     */
    public boolean changeUserInfo(RegisteredUserFormData user, HttpServletRequest request) {

        User currentUser = getCurrentUser(request);

        //
        String currentUserUsername = currentUser.getUsername();

        boolean ret = false;
        if (currentUserUsername.equals(user.getUsername())) {

            BeanUtils.copyProperties(user, currentUser);

            userRepository.save(currentUser);

            ret = true;
        }

        return ret;
    }

    public void sendRequestToRegisteredUser(String friendUsername, HttpServletRequest request) {

        User currentUser = getCurrentUser(request);

        // Provjera da li je vec poslat zahtjev

        if (userRepository.existsByUsername(friendUsername)) {

            User userReceiver = userRepository.findOneByUsername(friendUsername);

            Friendship friendship = new Friendship();
            friendship.setConfirmed(false);
            friendship.setFriendReceiver(userReceiver);
            friendship.setFriendRequester(currentUser);

            // Provjera da li zahtjev vec postoji
            // odnosno da li je receiver vec poslao zahtjev ili da li je requester vec poslao zahtjev
            friendshipRepository.save(friendship);
        }
    }

    public List<User> getFriendsOfCurrentUser(HttpServletRequest request) {

        List<User> friends = null;//friendshipRepository.findFriendsAsReceiver(username);
        //friends.addAll(friendshipRepository.findFriendsAsRequester(username));

        return friends;
    }

    public List<User> getRegisteredUsers(FindFriendForm friendForm, HttpServletRequest request) {

        User user = getCurrentUser(request);

        List<User> usersOfSameAuthority =
                userRepository.findByFirstNameContainsOrLastNameContainsOrEmailContainsAndActivatedIsTrueAndUsernameIsNotAndAuthoritiesIn(
                friendForm.getFirstName(),
                friendForm.getLastName(),
                friendForm.getEmail(),
                user.getUsername(),
                user.getAuthorities());

        return usersOfSameAuthority;
    }

    public Boolean checkEmail(String email, HttpServletRequest request) {

        boolean emailValid = false;

        User user = getCurrentUser(request);

        // Ako je unesen isti email ili ako ne postoji korisnik sa ovim emailom
        if (user.getEmail().equals(email) || !userRepository.existsByEmail(email)) {

            // Isti email koji korisnik vec ima
            emailValid = true;
        } else {

            // Vec postoji neko sa istim emailom
            emailValid = false;
        }

        return emailValid;
    }

    public Boolean checkUsername(String username, HttpServletRequest request) {

        boolean usernameValid = false;

        User user = getCurrentUser(request);

        // Validan username samo ako je isti kao ulogovani korisnik
        if (user.getUsername().equals(username)) {

            usernameValid = true;
        } else {

            usernameValid = false;
        }

        return usernameValid;
    }
}
