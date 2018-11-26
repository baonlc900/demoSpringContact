package com.example.demo.service;

import com.example.demo.domain.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    Iterable<Contact> getAllContact();
    Optional<Contact> getContactById(Integer id);
    Contact saveContact(Contact contact);
    void deleteContact(Integer id);
    List<Contact> search(String searchFirstName);
}
