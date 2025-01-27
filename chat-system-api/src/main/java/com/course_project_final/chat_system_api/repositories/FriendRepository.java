package com.course_project_final.chat_system_api.repositories;

import com.course_project_final.chat_system_api.entities.Friend;
import com.course_project_final.chat_system_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Integer> {

    @Query("SELECT f.friend FROM Friend f JOIN User u ON f.friend.id = u.id " +
            "WHERE f.user.id = :userId AND u.isDeleted = false AND f.isDeleted = false")
    List<User> findFriendsByUserId(@Param("userId") int userId);

    boolean existsByUserIdAndFriendIdAndIsDeletedFalse(int userId, int friendId);

    @Query("SELECT f FROM Friend f WHERE f.user.id = :userId AND f.friend.id = :friendId AND f.isDeleted = false")
    Optional<Friend> findFriendshipBetweenUsers(@Param("userId") int userId, @Param("friendId") int friendId);
}
