package com.api.eventos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.eventos.models.UserEventModel;

@Repository
public interface UserEventRepository extends JpaRepository<UserEventModel, Long> {
    List<UserEventModel> findByUserId(Long userId);
}
