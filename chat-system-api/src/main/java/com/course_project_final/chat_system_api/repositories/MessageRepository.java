package com.course_project_final.chat_system_api.repositories;

import com.course_project_final.chat_system_api.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE m.channel.id = :channelId AND m.isDeleted = false")
    List<Message> findChannelMessages(@Param("channelId") int channelId);

    @Query("SELECT m FROM Message m WHERE m.sender.id = :senderId AND m.receiver.id = :receiverId OR " +
            "m.sender.id = :receiverId AND m.receiver.id = :senderId AND m.isDeleted = false")
    List<Message> findDirectMessages(@Param("senderId") int senderId, @Param("receiverId") int receiverId);
}
