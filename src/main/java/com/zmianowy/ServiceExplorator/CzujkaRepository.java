package com.zmianowy.ServiceExplorator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CzujkaRepository extends JpaRepository<Czujka, Long> {



    Page<Czujka> findByServerContainingIgnoreCaseAndNameContainingIgnoreCaseAndMonitoringServer_Id(
            String server,
            String name,
            Long monitoringServerId,
            Pageable pageable);
    @Query("""
            select c from Czujka c
            where upper(c.server) like upper(concat('%', ?1, '%')) and upper(c.name) like upper(concat('%', ?2, '%'))""")
    Page<Czujka> findOnAllMonitoringServers(
            String server,
            String name,
            Long monitoringServerId,
            Pageable pageable);

}
