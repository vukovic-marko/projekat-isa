package isa.projekat.service;

import isa.projekat.model.Forms.RegisteredUserFormData;
import isa.projekat.model.RegisteredUser.Friendship;
import isa.projekat.model.User;
import isa.projekat.repository.FriendshipRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisteredUserService {

    @Autowired
    private isa.projekat.repository.UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    /**
     * Cuvaju se izmjenjeni podaci korisnika
     *
     * @param user podaci za izmjenu
     */
    public boolean changeUserInfo(RegisteredUserFormData user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        String currentUserUsername = currentUser.getUsername();

        boolean ret = false;
        if (currentUserUsername.equals(user.getUsername())) {

            BeanUtils.copyProperties(user, currentUser);

            userRepository.save(currentUser);

            ret = true;
        }

        return ret;
    }

    public void sendRequestToRegisteredUser(String friendUsername) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

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


    public List<User> getFriendOfUser(String username) {

      //  List<User> friends = friendshipRepository.findFriendsAsReceiver(username);
        //friends.addAll(friendshipRepository.findFriendsAsRequester(username));

       // return friends;
    	return null;
    }
}
