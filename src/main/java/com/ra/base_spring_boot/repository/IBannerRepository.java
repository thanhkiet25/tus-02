package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBannerRepository extends JpaRepository<Banner, Integer> {
    @Query("""
select b from Banner b
""")
    List<Banner> findAllBanners();
}
