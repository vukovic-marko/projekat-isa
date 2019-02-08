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
import java.util.ArrayList;
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

    public boolean sendRequestToRegisteredUser(Long friendId, HttpServletRequest request) {

        User currentUser = getCurrentUser(request);

        boolean ret = false;
        if (userRepository.exists(friendId)) {

            User userReceiver = userRepository.findOne(friendId);

            Friendship friendship = new Friendship();
            friendship.setConfirmed(false);
            friendship.setFriendReceiver(userReceiver);
            friendship.setFriendRequester(currentUser);

            friendshipRepository.save(friendship);

            ret = true;
        }

        return ret;
    }

    public List<User> getFriendsOfCurrentUser(HttpServletRequest request) {

        Long currentId = getCurrentUser(request).getId();

        //
        List<User> friends = new ArrayList<>();

        List<Friendship> asRequester = friendshipRepository.findByFriendRequester_IdAndConfirmedTrue(currentId);

        asRequester.forEach(friendship -> {

            friends.add(friendship.getFriendReceiver());
        });

        List<Friendship> asReceiver = friendshipRepository.findByFriendReceiver_IdAndConfirmedTrue(currentId);

        asReceiver.forEach(friendship -> {

            friends.add(friendship.getFriendRequester());
        });

        return friends;
    }

    public List<User> getRegisteredUsersNotFriends(FindFriendForm friendForm, HttpServletRequest request) {

        User user = getCurrentUser(request);

        List<Long> friendsIdList = new ArrayList<>();

        // extract receivers
        List<Friendship> requesterIdList = friendshipRepository.findByFriendRequester_Id(user.getId());
        requesterIdList.forEach(friendship -> {
            friendsIdList.add(friendship.getFriendReceiver().getId());
        });

        //extract requesters
        List<Friendship> receiverIdList = friendshipRepository.findByFriendReceiver_Id(user.getId());
        receiverIdList.forEach(friendship -> {
            friendsIdList.add(friendship.getFriendRequester().getId());
        });

        // exclude self from result
        friendsIdList.add(user.getId());

        // get users that are not friends based on form data
        List<User> usersOfSameAuthority =
                userRepository.findByFirstNameContainingAndLastNameContainingAndEmailContainingAndActivatedIsTrueAndAuthoritiesIsInAndIdIsNotInAllIgnoreCase(
                friendForm.getFirstName(),
                friendForm.getLastName(),
                friendForm.getEmail(),
                user.getAuthorities(),
                friendsIdList);

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

    public List<User> getSentRequests(HttpServletRequest request) {

        Long currentUserId = getCurrentUser(request).getId();

        List<User> sentRequests = new ArrayList<>();

        // Poslana neprihvacena prijateljstva
        List<Friendship> unconfirmedSentFriendships = friendshipRepository.findByFriendRequester_IdAndConfirmedFalse(currentUserId);

        unconfirmedSentFriendships.forEach(friendship -> {

            sentRequests.add(friendship.getFriendReceiver());
        });

        return sentRequests;
    }

    public List<User> getReceivedRequests(HttpServletRequest request) {

        Long currentUserId = getCurrentUser(request).getId();

        List<User> receivedRequests = new ArrayList<>();

        // Poslana neprihvacena prijateljstva
        List<Friendship> unconfirmedReceivedFriendships = friendshipRepository.findByFriendReceiver_IdAndConfirmedFalse(currentUserId);

        unconfirmedReceivedFriendships.forEach(friendship -> {

            receivedRequests.add(friendship.getFriendRequester());
        });

        return receivedRequests;
    }

    public void acceptRequest(Long idRequester, HttpServletRequest request) {

        Friendship friendship = friendshipRepository.findByFriendReceiver_IdAndFriendRequester_IdAndConfirmedFalse(getCurrentUser(request).getId(), idRequester);

        friendship.setConfirmed(true);

        friendshipRepository.save(friendship);
    }

    public void cancelRequest(Long idReceiver, HttpServletRequest request) {

        Long currentId = getCurrentUser(request).getId();

        Friendship friendship = friendshipRepository.findByFriendReceiver_IdAndFriendRequester_IdAndConfirmedFalse(idReceiver, currentId);

        friendshipRepository.delete(friendship);
    }

    public void deleteFriend(Long friendId, HttpServletRequest request) {

        Friendship friendship = friendshipRepository.findByFriendReceiver_IdAndFriendRequester_IdAndConfirmedTrue(friendId, getCurrentUser(request).getId());

        if(friendship == null) {

            friendship = friendshipRepository.findByFriendReceiver_IdAndFriendRequester_IdAndConfirmedTrue(getCurrentUser(request).getId(), friendId);
        }

        friendshipRepository.delete(friendship);
    }

}
