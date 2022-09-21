package com.example.citylist.repo;

import com.example.citylist.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<City, Long> {

    Page<City> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
