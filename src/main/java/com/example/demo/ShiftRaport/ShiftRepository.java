package com.example.demo.ShiftRaport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<ShiftPost,Long> {

    Page<ShiftPost> findAllByVisibleIsTrueOrderByEditedDateDesc(Pageable pageable);

    @Query("SELECT s FROM ShiftPost s WHERE s.id = ?1 order by s.id desc ")
    Optional<ShiftPost> findShiftPostById(Long id);

    @Query("SELECT s.autor, COUNT(s) FROM ShiftPost s GROUP BY s.autor ORDER BY COUNT(s) DESC")
    List<Object[]> getAuthorStatistics();

    List<ShiftPost> findByContentContainingIgnoreCaseAndTitleContainingIgnoreCaseAndStateContainingIgnoreCaseAndLocalDateBetween(String content, String title,String state, String startDate, String endDate);
    List<ShiftPost> findByContentContainingIgnoreCaseAndTitleContainingIgnoreCaseAndStateContainingIgnoreCaseAndLocalDateBetweenAndVisible(String content, String title,String state, String startDate, String endDate,Boolean visible);



    @Modifying
    @Query("UPDATE ShiftPost s SET s.visible = :isVisible WHERE s.id = :id")
    int updateVisibilityById(@Param("id") Long id, @Param("isVisible") Boolean visible);
}