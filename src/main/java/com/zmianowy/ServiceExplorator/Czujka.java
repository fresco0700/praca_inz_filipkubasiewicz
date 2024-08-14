package com.zmianowy.ServiceExplorator;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table
public class Czujka {
    @Id
    @SequenceGenerator(
            name = "czujka_sequence",
            sequenceName = "czujka_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "czujka_sequence"
    )
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "monitoring_server_id")
    private MonitoringServer monitoringServer;
    private String name;
    private String server;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String admin;


    public Czujka(Long monitoringServerId, String server, String name, String admin, String description, MonitoringServerService monitoringServerService) {
        this.monitoringServer = monitoringServerService.getMonitoringServerById(monitoringServerId);
        this.name = name;
        this.server = server;
        this.description = description;
        this.admin = admin;
    }

    public String getMonitoringServerName() {
        return monitoringServer.getName();
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public MonitoringServer getMonitoringServer() {
        return monitoringServer;
    }

    public void setMonitoringServer(MonitoringServer monitoringServer) {
        this.monitoringServer = monitoringServer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
