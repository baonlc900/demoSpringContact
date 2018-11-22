package com.example.demo.service;

import com.example.demo.domain.Contact;
import com.example.demo.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;
    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Iterable<Contact> getAllContact() {
        return this.contactRepository.findAll();
    }

    @Override
    public Optional<Contact> getContactById(Integer id) {
        return this.contactRepository.findById(id);
    }

    @Override
    public Contact saveContact(Contact contact) {
        return this.contactRepository.save(contact);
    }

    @Override
    public void deleteContact(Integer id) {
        this.contactRepository.deleteById(id);
    }
}
