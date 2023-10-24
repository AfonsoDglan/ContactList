package com.testeentrevista.crud.model.service;

import com.testeentrevista.crud.model.entity.Contact;
import com.testeentrevista.crud.model.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
public class ContactService {
    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }
    public List<Contact> all()
    {
        return repository.findAll();
    };

    public Contact create(Contact contact)
    {
        return repository.save(contact);
    };

    public Contact update(Contact contact)
    {
        return repository.save(contact);
    }
    public  void delete(Long id)
    {
        repository.deleteById(id);
    };
}
