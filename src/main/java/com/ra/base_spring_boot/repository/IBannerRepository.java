package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBannerRepository extends JpaRepository<Banner, Integer> {
    @Query("""
select b from Banner b
""")
    List<Banner> findAllBanners();

    @Query("""
        SELECT b FROM Banner b
        WHERE (:keyword IS NULL OR LOWER(b.position) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(b.bannerUrl) LIKE LOWER(CONCAT('%', :keyword, '%')))
        """)
    Page<Banner> getAllAndSearchBanner(@Param("keyword") String keyword, Pageable pageable);
}
