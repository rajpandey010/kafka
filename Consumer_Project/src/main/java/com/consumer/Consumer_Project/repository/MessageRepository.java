package com.consumer.Consumer_Project.repository;

import com.consumer.Consumer_Project.modal.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface MessageRepository extends JpaRepository<MessageModel,String> {
}
