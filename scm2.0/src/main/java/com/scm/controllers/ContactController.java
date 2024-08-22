package com.scm.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;
import com.scm.services.UserService;
import com.scm.services.imageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private imageService imageService;

    //add contact page: handler
    @RequestMapping("/add")
    public String addContactView(Model model) {

        ContactForm contactForm = new ContactForm();
        // contactForm.setName("Miss Duan");
        contactForm.setFavorite(false);
        model.addAttribute("contactForm",contactForm);

        return "user/add_contact";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result,Authentication authentication,HttpSession session) {
        
        //process the form data

        // validate form
        if(result.hasErrors()) {
            session.setAttribute("message",
            Message.builder()
                .content("Please correct the following errors")
                .type(MessageType.red)
                .build());
            return "user/add_contact";
        }
        
        String username = Helper.getEmailOfLoggedInUser(authentication);
        //form ---> contact


        User user = userService.getUserByEmail(username);
        //process the contact picture
        
        // logger.info("File Information : {}",contactForm.getContactImage().getOriginalFilename());
        //image process
        String filename = UUID.randomUUID().toString();

        String fileURL = imageService.uploadImage(contactForm.getContactImage(),filename);


        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setPicture(fileURL);
        contact.setCludinaryImagePublicId(filename);
        contactService.save(contact);
        System.out.println(contactForm);

        //3.set the contact picture url

        //set message to be displayed on the view
        session.setAttribute("message",
        Message.builder()
            .content("You have successfully added a new contact")
            .type(MessageType.green)
            .build());
        return "redirect:/user/contacts/add";
    }

    //view contacts
    @RequestMapping
    public String viewContacts(
        @RequestParam(value = "page",defaultValue = "0") int page,
        @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size,
        @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
        @RequestParam(value = "direction",defaultValue = "asc") String direction,Model model,Authentication authentication) {

        //load all user contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);
        
        User user = userService.getUserByEmail(username);


        Page<Contact> pageContact = contactService.getByUser(user,page,size,sortBy,direction);

        model.addAttribute("pageContact",pageContact);
        model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    //search handler
    @GetMapping("/search")
    public String searchHandler(
        @ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value = "size",defaultValue=AppConstants.PAGE_SIZE + "") int size,
        @RequestParam(value = "page",defaultValue = "0") int page,
        @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
        @RequestParam(value = "direction",defaultValue = "asc") String direction,
        Model model,
        Authentication authentication
        ) {

        logger.info("field {} keyword {}",contactSearchForm.getField(),contactSearchForm.getValue());
        
        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact>pageContact = null;
        if(contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        }
        logger.info("pageContact {}",pageContact);
        model.addAttribute("contactSearchForm",contactSearchForm);
        model.addAttribute("pageContact",pageContact);
        model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
        
        return "user/search";
    }

}
