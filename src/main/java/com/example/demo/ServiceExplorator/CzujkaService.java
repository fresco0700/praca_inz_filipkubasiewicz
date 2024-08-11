package com.example.demo.ServiceExplorator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CzujkaService {

    private final CzujkaRepository czujkaRepository;
    private final MonitoringServerService monitoringServerService;

    @Transactional
    public void updateCzujka(Long id, String server, String name, String admin, String description){
        Czujka czujka = czujkaRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Ta czujka już nie istnieje"));
        czujka.setServer(server);
        czujka.setName(name);
        czujka.setAdmin(admin);
        czujka.setDescription(description);
    }


    public void addCzujka(Long monitoringServerId, String server, String name, String admin, String description) {
        Czujka czujka = new Czujka(monitoringServerId, server, name, admin, description, monitoringServerService);
        czujkaRepository.save(czujka);
    }

    public void deleteCzujka(Long id){czujkaRepository.deleteById(id);}
    public Page<Czujka> searchByNameAndMonitoringServerId(
                                                        String serverName,
                                                        String serviceName,
                                                        Long monitoringServer,
                                                        Pageable pageable)
    {
        if (monitoringServer != 0){
            return czujkaRepository
                    .findByServerContainingIgnoreCaseAndNameContainingIgnoreCaseAndMonitoringServer_Id(
                            serverName,
                            serviceName,
                            monitoringServer,
                            pageable);}
        else {
            return czujkaRepository
                    .findOnAllMonitoringServers(serverName,
                            serviceName,
                            monitoringServer,
                            pageable);}
    }
}