package com.example.demo.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SenderTemplateService {

    @Autowired
    private SenderTemplateRepository senderTemplateRepository;

    @Autowired
    private ContactRepository contactRepository;



    public List<SenderTemplateDTO> getAllSenderTemplateDTOs() {
        List<SenderTemplate> allTemplates = senderTemplateRepository.findAll();

        return allTemplates.stream().map(senderTemplate -> {
            SenderTemplateDTO dto = new SenderTemplateDTO();
            dto.setId(senderTemplate.getId());
            dto.setName(senderTemplate.getName());
            dto.setLastSend(senderTemplate.getLastSend());
            dto.setCreatedDate(senderTemplate.getCreatedDate());
            dto.setCreator(senderTemplate.getCreator());
            dto.setLastEditor(senderTemplate.getLastEditor());
            dto.setLastEdited(senderTemplate.getLastEdited());

            dto.setContactNames(senderTemplate.getContacts().stream()
                    .map(Contact::getName)
                    .collect(Collectors.toList()));
            dto.setContentSms(senderTemplate.getContentSms());
            dto.setContentMail(senderTemplate.getContentMail());
            return dto;
        }).collect(Collectors.toList());
    }
    public String getUserFromSession() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return String.valueOf(username);
    }
}

