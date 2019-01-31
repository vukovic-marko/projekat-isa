package isa.projekat.controller.UserProfileControllers;


import isa.projekat.model.User;
import isa.projekat.service.CustomUserDetailsService;
import isa.projekat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(value = "/user/profile")
public class UserProfileViewController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping(value = "")
    public String profileIndex(Model model) {


        return "user/profile";
    }

    @GetMapping(value = "/info")
    public String getProfileInfo(Model model, Principal principal) {

        // insert profile info into model

        String username = "admin";
        User user = (User)userDetailsService.loadUserByUsername(username);

        model.addAttribute("title", "Profile info");
        model.addAttribute("user", user);
        return "user/fragments/profileInfo :: profileInfoForm";
    }

    @GetMapping(value = "/friends")
    public String getFriends(Model model) {

        // insert friends into model

        return "user/fragments/friends :: friendsList";
    }

    @GetMapping(value = "/requests/pending")
    public String getPendingReq(Model model) {

        // insert pending requests into model

        return "user/fragments/requests :: pendingRequestsList";
    }

    @GetMapping(value = "/requests/sent")
    public String getSentReq(Model model) {

        // insert friends into model

        return "user/fragments/requests :: sentRequestsList";
    }

}
