package com.example.demo.controller;

import com.example.demo.domain.Contact;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/contacts")
public class ContactController {
    private ContactService contactService;

    @Autowired
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = {"", "/"})
    public String index(Model model) {
        model.addAttribute("activePage", "contacts");
        model.addAttribute("contacts", this.contactService.getAllContact());
        return "contact/index";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String addContactForm(Contact contact, Model model) {
        model.addAttribute("activePage", "contacts");
        return "contact/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addContact(@Valid Contact contact, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activePage", "contacts");
            return "contact/add";
        }

        this.contactService.saveContact(contact);
        return "redirect:/contacts";
    }

    @RequestMapping(value = "/view/{id}")
    public String viewContact(@PathVariable Integer id, Model model) {
        Optional<Contact> opt = this.contactService.getContactById(id);
        Contact con = opt.get();
        model.addAttribute("contact",con );
        model.addAttribute("activePage", "contacts");
        return "contact/view";
    }

    @RequestMapping(value = "/edit/{id}")
    public String editContact(@PathVariable Integer id, Model model) {
        Optional<Contact> opt = this.contactService.getContactById(id);
        Contact con = opt.get();
        model.addAttribute("contact", con);
        model.addAttribute("activePage", "contacts");
        return "contact/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateContact(Contact contact) {
        System.out.println("Contact ID: " + contact.getId());
        this.contactService.saveContact(contact);
        return "redirect:/contacts/view/" + contact.getId();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteContact(@PathVariable Integer id) {
        this.contactService.deleteContact(id);
        return "redirect:/contacts";
    }

}
