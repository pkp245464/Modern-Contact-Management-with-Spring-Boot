package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home Page Handler");
        model.addAttribute("name","Substring Technology");
        model.addAttribute("leetcode","Learn coding with pankaj");
        model.addAttribute("githubRepo","https://github.com/pkp245464");
        
        return "home";
    }
    //about route
    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin",true);
        System.out.println("About Page Loading");
        return "about";
    }

    //service
    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("Services Page Loading");
        return "services";
    }

    //Contact
    @GetMapping("/contact")
    public String contact() {
        return new String("Contact");
    }

    //login
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    

    //signup
    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        // userForm.setName("PANKAJ");
        // userForm.setEmail("pankajkumar245464@gmail.com");
        // userForm.setPassword("sde$pkp");
        // userForm.setPhoneNumber("+917607300350");
        // userForm.setAbout("I am SDE Developer");

        model.addAttribute("userForm", userForm);
        return new String("register");
    }
    
    //processing register
    @RequestMapping(value = "/do-register",method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult,HttpSession session) {
        System.out.println("Processing registration");
        // fetch form data
            //User Form
        System.out.println(userForm);

        // validate form data
        if(rBindingResult.hasErrors()) {
            return "register";
        }
        

        // save to database
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("/src/main/resources/static/images/prfoilePick.jpg")
        // .build();
        User user  = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(user.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("/src/main/resources/static/images/prfoilePick.jpg");
        User saveUser = userService.saveUser(user);
        System.out.println("User saved: ");
        //message = "Registration Sucessful"
        //add the message

        Message message = Message.builder().content("Registration Sucessful").type(MessageType.green).build();
        session.setAttribute("message",message);
        

        // redirect the login page
        return "redirect:/register";
    }
}
