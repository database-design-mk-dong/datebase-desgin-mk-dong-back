package com.example.demo.repository;

import com.example.demo.domain.Farm;
import com.example.demo.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    List<Farm> findAllByUser(User user);


    Optional<Farm> findByUserAndId(User user, Long Id);
}
