package com.sun.vuebackground.dao;

import com.sun.vuebackground.entity.TB_user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TB_user_dao extends JpaRepository<TB_user, String>, JpaSpecificationExecutor<TB_user> {
    int countByUsername(String username);

    TB_user findByUsername(String username);

    int countById(String username);
}
