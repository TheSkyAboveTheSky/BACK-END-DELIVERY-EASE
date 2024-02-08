package com.project.isima.repositories;

import com.project.isima.entities.Sender;
import com.project.isima.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderRepository extends JpaRepository<User, Long> {
    User findSenderById(Long userId);
}
