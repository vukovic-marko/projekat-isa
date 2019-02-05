package isa.projekat.controller.UserProfileControllers;


import isa.projekat.model.Forms.FindFriendForm;
import isa.projekat.model.Forms.RegisteredUserFormData;
import isa.projekat.model.User;
import isa.projekat.service.RegisteredUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/registereduser/profile")
@PreAuthorize("hasRole('ROLE_USER')")
public class UserProfileViewController {

    @Autowired
    private RegisteredUserService registeredUserService;

    @ModelAttribute
    public void currentUser(Model model) {

        // add title
        model.addAttribute("title", "Profile");

        // add current user info to data
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        RegisteredUserFormData userFormData = new RegisteredUserFormData();
        BeanUtils.copyProperties(currentUser, userFormData);

        model.addAttribute("user", userFormData);
    }

    @GetMapping(value = "")
    public String profileIndex() {

//        return "user/profile :: #userProfile";

        return "user/profile :: userProfileFrag";
    }

    @GetMapping(value = "/info")
    public String getProfileInfo() {

        return "user/fragments/profileInfo :: profileInfoForm";
    }

    @GetMapping(value = "/friends")
    public String getFriends(Model model, HttpServletRequest request) {

        List<User> friends = registeredUserService.getFriendsOfCurrentUser(request);

        model.addAttribute("friendsList", friends);

        return "user/fragments/friends :: friendsList";
    }

    @GetMapping(value = "/friends/add")
    public String addFriend(@ModelAttribute("friendQuery") @Valid FindFriendForm findFriendsForm, Model model, HttpServletRequest request) {

        //List<User> users = registeredUserService.getRegisteredUsers(findFriendsForm, request);

        //model.addAttribute("users", users);
        return "user/fragments/addFriend";
    }

    @GetMapping(value = "/requests/pending")
    public String getPendingReq(Model model) {

        // insert pending requests into model

        return "user/fragments/requests :: pendingRequestsList";
    }

    @GetMapping(value = "/requests/sent")
    public String getSentReq(Model model) {

        // insert sent requests into model

        return "user/fragments/requests :: sentRequestsList";
    }

    @GetMapping(value = "/validity/checkusername")
    @ResponseBody
    public Boolean checkUsername(@RequestParam(name = "username") String username, HttpServletRequest request) {

        return registeredUserService.checkUsername(username, request);
    }

    @GetMapping(value = "/validity/checkemail")
    @ResponseBody
    public Boolean checkEmail(@RequestParam(name = "email") String email, HttpServletRequest request) {

        return registeredUserService.checkEmail(email, request);
    }
}
