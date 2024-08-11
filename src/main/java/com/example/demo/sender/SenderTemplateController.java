package com.example.demo.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(path = "/sendersms")
public class SenderTemplateController {

    @Autowired
    private SenderTemplateRepository senderTemplateRepository;

    @Autowired
    private SenderTemplateService senderTemplateService;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactService contactService;

    @GetMapping(path = "/templates")
    public String showTemplates(Model model) {
        List<SenderTemplateDTO> allTemplateDTOs = senderTemplateService.getAllSenderTemplateDTOs();
        List<ContactDTO> allContactsDTOs = contactService.getAllContacts();
        for (SenderTemplateDTO template : allTemplateDTOs) {
            System.out.println(template.getContacts());
        }
        model.addAttribute("allTemplates", allTemplateDTOs);
        model.addAttribute("allContacts",allContactsDTOs);
        return "sendertemplate";
    }

    @PostMapping("/deleteTemplate")
    public String deleteTemplate(@RequestParam Long templateId) {
        senderTemplateRepository.deleteById(templateId);
        return "redirect:/sendersms/templates";
    }

    @PostMapping("/editTemplate")
    public String editTemplate(@RequestParam Long templateId, @RequestParam String name) {
        SenderTemplate senderTemplate = senderTemplateRepository.findById(templateId).orElseThrow(() -> new IllegalArgumentException("Invalid template Id:" + templateId));
        senderTemplate.setName(name);
        senderTemplate.setLastEditor(senderTemplateService.getUserFromSession());
        senderTemplate.setLastEdited(LocalDateTime.now());
        senderTemplateRepository.save(senderTemplate);
        return "redirect:/sendersms/templates";
    }

    @PostMapping("/addTemplate")
    public String addTemplate(
                                @RequestParam String name,
                                @RequestParam List<Long> contactIds,
                                @RequestParam String contentMail,
                                @RequestParam String contentSms
                                ){
        SenderTemplate senderTemplate = new SenderTemplate();
        LocalDateTime now = LocalDateTime.now();
        senderTemplate.setName(name); //Nazwa szablonu
        senderTemplate.setContentMail(contentMail); //Zawartosc maila
        senderTemplate.setLastEdited(now); //Data ostatniej edycji
        senderTemplate.setCreatedDate(now); //Data stworzenia
        senderTemplate.setCreator(senderTemplateService.getUserFromSession());//Uzytkownik ktory stworzyl
        senderTemplate.setLastEditor(senderTemplateService.getUserFromSession());//Uzytkownik ktory edytowal
        senderTemplate.setContentSms(contentSms);//Zawartosc SMS


        List<Contact> contacts = contactRepository.findAllById(contactIds);
        senderTemplate.setContacts(contacts); // ustawienie kontaktów

        senderTemplateRepository.save(senderTemplate);

        return "redirect:/sendersms/templates";
    }
}
