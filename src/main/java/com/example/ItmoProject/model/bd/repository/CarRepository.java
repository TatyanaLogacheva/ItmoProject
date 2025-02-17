package com.example.ItmoProject.model.bd.repository;

import com.example.ItmoProject.model.bd.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository <Car, Long> {

    @Query("select с from Car с where с.brand like %:filter% or с.model like %:filter% ")
    Page<Car> findAllFiltered(Pageable pageRequest, @Param("filter") String filter);
}
