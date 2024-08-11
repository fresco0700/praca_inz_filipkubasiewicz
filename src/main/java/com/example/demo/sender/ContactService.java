package com.example.demo.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactDTO convertToDto(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setName(contact.getName());
        contactDTO.setSurname(contact.getSurname());
        contactDTO.setPhoneNumber(contact.getPhoneNumber());
        contactDTO.setCompany(contact.getCompany());
        contactDTO.setEmail(contact.getEmail());
        return contactDTO;

    }

    public List<ContactDTO> getAllContacts() {
    List<Contact> contactDTOList = contactRepository.findAll();
    return contactDTOList.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
    }

}
