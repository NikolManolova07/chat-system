package com.course_project_final.chat_system_api.repositories;

import com.course_project_final.chat_system_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE " +
            "(LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            " LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            " LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            " LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND u.isDeleted = false")
    List<User> searchUsersByKeyword(@Param("keyword") String keyword);

    Optional<User> findByIdAndIsDeletedFalse(int id);
}
