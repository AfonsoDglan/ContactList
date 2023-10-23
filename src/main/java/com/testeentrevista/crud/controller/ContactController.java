package com.testeentrevista.crud.controller;

import com.testeentrevista.crud.model.entity.Contact;
import com.testeentrevista.crud.model.service.ContactService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/contato")
@AllArgsConstructor
public class ContactController {
    @Autowired
    ContactService contactService;

    @GetMapping("/list")
    public ResponseEntity<?> fetchAll(){
        return new ResponseEntity<>(contactService.all(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Contact contact)
    {
        return new ResponseEntity<Contact>(contactService.create(contact), HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> upade(@Valid @RequestBody Contact contact)
    {
        return new ResponseEntity<Contact>(contactService.update(contact), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Contact contact)
    {
        contactService.delete(contact.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
