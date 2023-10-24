package com.testeentrevista.crud.controller;

import com.testeentrevista.crud.model.entity.Contact;
import com.testeentrevista.crud.model.service.ContactService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public ResponseEntity<Contact> save(@Valid Contact contact, @RequestParam("file") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String uploadDir = "uploads/";
                String filePath = Paths.get(uploadDir, fileName).toString();
                String absolutePath = new File("").getAbsolutePath();
                File uploadPath = new File(absolutePath + "/" + uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                Path path = Paths.get(uploadPath.getAbsolutePath() + File.separator + fileName);
                Files.write(path, file.getBytes());
                contact.setImage(uploadDir + fileName);
            }


            return new ResponseEntity<Contact>(contactService.create(contact), HttpStatus.CREATED);
        } catch (IOException e) {
            // Lida com erros de upload
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/image/{imageFileName:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String imageFileName) {
        String uploadDir = "uploads/";

        try {
            Path imagePath = Paths.get(uploadDir).resolve(imageFileName);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
