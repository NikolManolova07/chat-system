package com.course_project_final.chat_system_api.repositories;

import com.course_project_final.chat_system_api.entities.Channel;
import com.course_project_final.chat_system_api.entities.ChannelMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Integer> {

    @Query("SELECT cm.channel FROM ChannelMember cm JOIN Channel c ON cm.channel.id = c.id " +
            "WHERE cm.user.id = :userId AND c.isDeleted = false AND cm.isDeleted = false")
    List<Channel> findChannelsByUserId(@Param("userId") int userId);

    Optional<ChannelMember> findByChannelIdAndUserIdAndIsDeletedFalse(int channelId, int userId);

    boolean existsByChannelIdAndUserIdAndIsDeletedFalse(int channelId, int userId);

    List<ChannelMember> findByChannelIdAndIsDeletedFalse(int channelId);

    @Modifying
    @Query("UPDATE ChannelMember cm SET cm.isDeleted = true WHERE cm.channel.id = :channelId AND cm.user.id = :userId")
    int softDeleteByChannelIdAndUserId(@Param("channelId") int channelId, @Param("userId") int userId);
}
