package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    //lấy danh sách user có số lần đặt vé >= 2
    @Query("SELECT u, COUNT(p) AS totalCount " +
            "FROM User u LEFT JOIN u.payments p" +
            " GROUP BY u " +
            "HAVING COUNT(p) >= 2")
    List<Object[]> findLoyalCustomers();

    //lấy danh sách user có số lần đặt vé = 1
    @Query("SELECT u, COUNT(p) AS totalCount " +
            "FROM User u LEFT JOIN u.payments p " +
            "GROUP BY u " +
            "HAVING COUNT(p) = 1")
    List<Object[]> findNewCustomers();

    // đếm số user có số lần đặt vé >= 2
    @Query("SELECT COUNT(u) " +
            "FROM User u " +
            "WHERE (SELECT COUNT(p) FROM u.payments p) >= 2")
    Long countLoyalCustomers();

    // đếm số user có số lần đặt vé =1
    @Query("SELECT COUNT(u) " +
            "FROM User u " +
            "WHERE (SELECT COUNT(p) FROM u.payments p) = 1")
    Long countNewCustomers();
}
