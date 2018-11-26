package com.example.demo.repository;

import com.example.demo.domain.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact,Integer>, QueryByExampleExecutor<Contact> {
    List<Contact> findByFirstName(String firstName);
}
