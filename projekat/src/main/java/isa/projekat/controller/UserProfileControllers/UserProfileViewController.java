package isa.projekat.controller.UserProfileControllers;


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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return "user/profile";
    }

    @GetMapping(value = "/info")
    public String getProfileInfo() {

        return "user/fragments/profileInfo :: profileInfoForm";
    }

    @GetMapping(value = "/friends")
    public String getFriends(Model model) {

        //
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        model.addAttribute("friendsList", registeredUserService.getFriendOfUser(currentUser.getUsername()));

        return "user/fragments/friends :: friendsList";
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

}
