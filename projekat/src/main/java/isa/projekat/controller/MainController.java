
package isa.projekat.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import isa.projekat.model.Authority;
import isa.projekat.model.User;
import isa.projekat.security.TokenUtils;
import isa.projekat.service.CustomUserDetailsService;

@Controller
public class MainController {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    TokenUtils tokenUtils;

	@GetMapping(value = "/")
    public ModelAndView method(HttpServletRequest request,@RequestParam(required=false) String token) {
		
		if (token == null || token.equals(""))
			return new ModelAndView("redirect:" + "index.html");
		System.out.println(token);
		String uname = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(uname);
        if (user == null)
            return new ModelAndView("redirect:" + "index.html");
 
        if (user != null) {
            List<Authority> li = (List<Authority>) user.getAuthorities();
            System.out.println(li.get(0).getName() + " <-----------");
            if (li.get(0).getName().equals("ROLE_RENT_A_CAR_ADMIN"))
                return new ModelAndView("redirect:" + "racadmin.html");
            if (li.get(0).getName().equals("ROLE_SYSTEM_ADMIN"))
            	return new ModelAndView("redirect:" + "sysadmin.html");

            if (li.get(0).getName().equals("ROLE_USER"))
            	return new ModelAndView("redirect:" + "user.html");

            // dodata stranica za administratora hotela, Marko
            if (li.get(0).getName().equals("ROLE_HOTEL_ADMIN"))
            	return new ModelAndView("redirect:" + "hoteladmin.html");

        }
        return new ModelAndView("redirect:" + "index.html");
	}
}

