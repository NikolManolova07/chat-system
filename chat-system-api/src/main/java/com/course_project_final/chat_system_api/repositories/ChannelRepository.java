package com.course_project_final.chat_system_api.repositories;

import com.course_project_final.chat_system_api.entities.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {

    @Modifying
    @Query("UPDATE Channel c SET c.isDeleted = true WHERE c.id = :id")
    int softDeleteById(@Param("id") int id);

    Optional<Channel> findByIdAndIsDeletedFalse(int id);
}
