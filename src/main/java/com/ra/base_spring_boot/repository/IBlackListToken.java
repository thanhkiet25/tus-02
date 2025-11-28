package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IBlackListToken extends JpaRepository<BlackListToken, Long> {
    boolean existsByToken(String token);

    @Modifying
    @Query("DELETE FROM BlackListToken b where b.isLogin = false ")
    void deleteAllByIsLoginFalse();

}
