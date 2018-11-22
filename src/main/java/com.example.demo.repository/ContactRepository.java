package com.example.demo.repository;

import com.example.demo.domain.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact,Integer> {

}
