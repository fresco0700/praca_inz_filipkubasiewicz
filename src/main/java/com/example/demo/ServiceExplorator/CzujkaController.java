package com.example.demo.ServiceExplorator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/szukajczujki")
public class CzujkaController {

    private final CzujkaService czujkaService;


    @GetMapping
    public String showPage() {
        return "explorator";
    }

    @GetMapping("/find")
    public String searchByName(Model model,
                               @RequestParam("servername") String serverName,
                               @RequestParam("servicename") String serviceName,
                               @RequestParam(value = "monitoringServer", defaultValue = "1") Long monitoringServer) {
        Pageable pageable = PageRequest.of(0, 100);
        Page<Czujka> czujki = czujkaService
                .searchByNameAndMonitoringServerId(
                        serverName,
                        serviceName,
                        monitoringServer,
                        pageable);
        model.addAttribute("results", czujki.getContent());
        return "explorator";
    }

    @PostMapping(path = "/edit")
    public String editById(@RequestParam("id") Long id,
                           @RequestParam("server") String server,
                           @RequestParam("name") String name,
                           @RequestParam("admin") String admin,
                           @RequestParam("description") String description,
                           HttpServletRequest request) {

        czujkaService.updateCzujka(id, server, name, admin, description);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @PostMapping(path = "/add")
    public String addService(@RequestParam("monitoringserverid") Long monitoringServerId,
                             @RequestParam("server") String server,
                             @RequestParam("name") String name,
                             @RequestParam("admin") String admin,
                             @RequestParam("description") String description,
                             HttpServletRequest request) {
        czujkaService.addCzujka(monitoringServerId, server, name, admin, description);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping(path = "/delete")
    public String deleteService(@RequestParam("id") Long id, HttpServletRequest request) {

        czujkaService.deleteCzujka(id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
