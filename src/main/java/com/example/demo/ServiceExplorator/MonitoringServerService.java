package com.example.demo.ServiceExplorator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoringServerService {

    private final MonitoringServerRepository monitoringServerRepository;

    public MonitoringServer getMonitoringServerById(Long id) {
        return monitoringServerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MonitoringServer not found with id " + id));
    }
}

