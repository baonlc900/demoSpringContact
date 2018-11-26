package com.example.demo.controller;

import com.example.demo.domain.Contact;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.List;
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
    public String index(Model model,HttpServletRequest request, RedirectAttributes attr) {
        request.getSession().setAttribute("listContacts",null);
        if (model.asMap().get("success") != null)
            attr.addFlashAttribute("success", model.asMap().get("success").toString());
        return "redirect:/contacts/page/1";

//        model.addAttribute("activePage", "contacts");
//        model.addAttribute("contacts", this.contactService.getAllContact());
//        return "contact/index";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String addContactForm(Contact contact, Model model) {
        if (!model.containsAttribute(""))
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
    public String updateContact(@Valid Contact contact,BindingResult result) {
        System.out.println("Contact ID: " + contact.getId());
        if(result.hasErrors()) {
            return "contact/edit";
        }
        this.contactService.saveContact(contact);
        return "redirect:/contacts/view/" + contact.getId();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteContact(@PathVariable Integer id) {
        this.contactService.deleteContact(id);
        return "redirect:/contacts";
    }

    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public String showContactPage(Model model, @PathVariable int pageNumber, HttpServletRequest request) {
        PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listContacts");
        int pagesize = 5;
        List<Contact> list = (List<Contact>) contactService.getAllContact();
        System.out.println(list.size());
        if (pages == null) {
            pages = new PagedListHolder<>(list);
            pages.setPageSize(pagesize);
        } else {
            final int gotoPage = pageNumber - 1;
            if (gotoPage <= pages.getPageCount() && gotoPage >= 0) {
                pages.setPage(gotoPage);
            }
        }
        request.getSession().setAttribute("listContacts", pages);

        int current = pages.getPage() + 1;
        int begin = Math.max(1, current - list.size());
        int end = Math.min(begin + 5, pages.getPageCount());
        int totalPageCount = pages.getPageCount();

        String baseUrl = "/contacts/page/";

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("contacts", pages);

        return "contact/index";

    }

    // Find Contact List
    @RequestMapping(value = "/search/{pageNumber}", method = RequestMethod.GET)
    public String searchContact(@RequestParam(value = "s", required = false) String s, Model model,
                                HttpServletRequest request, @PathVariable int pageNumber) {
        if (s.equals("")) {
            return "redirect:/contacts";
        }


        List<Contact> list = contactService.search(s);
        if (list == null) {
            return "redirect:/contacts";
        }

        PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listContacts");
        int pagesize = 5;

        pages = new PagedListHolder<>(list);
        pages.setPageSize(pagesize);

        final int gotoPage = pageNumber - 1;
        if (gotoPage <= pages.getPageCount() && gotoPage >= 0) {
            pages.setPage(gotoPage);
        }

        request.getSession().setAttribute("listContacts", pages);

        int current = pages.getPage() + 1;
        int begin = Math.max(1, current - list.size());
        int end = Math.min(begin + 5, pages.getPageCount());
        int totalPageCount = pages.getPageCount();

        String baseUrl = "/contacts/page/";

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("contacts", pages);

        return "contact/index";

    }
}
