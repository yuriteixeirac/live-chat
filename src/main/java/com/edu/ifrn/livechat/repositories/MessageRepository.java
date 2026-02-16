package com.edu.ifrn.livechat.repositories;

import com.edu.ifrn.livechat.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("""
        SELECT m FROM Message m WHERE
            (m.from.username = :firstUser AND m.to.username = :secondUser) OR
            (m.to.username = :firstUser AND m.from.username = :secondUser)
        ORDER BY m.sentAt DESC
    """)
    Page<Message> findMessagesBetweenUsers(String firstUser, String secondUser, Pageable pageable);
}
