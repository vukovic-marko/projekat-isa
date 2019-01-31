
package isa.projekat.controller;

import isa.projekat.model.Authority;
import isa.projekat.model.User;
import isa.projekat.security.TokenUtils;
import isa.projekat.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    TokenUtils tokenUtils;

	@RequestMapping(value = "/")
    public ModelAndView method(HttpSession session) {
        String un = (String) session.getAttribute("user");
        if (un == null)
            return new ModelAndView("redirect:" + "index.html");
        User u = (User) userDetailsService.loadUserByUsername(un);
        if (u != null) {
            List<Authority> li = (List<Authority>) u.getAuthorities();
            if (li.get(0).getName().equals("ROLE_RENT_A_CAR_ADMIN"))
                return new ModelAndView("redirect:" + "racadmin.html");
        }
        return new ModelAndView("redirect:" + "index.html");
	}
}

