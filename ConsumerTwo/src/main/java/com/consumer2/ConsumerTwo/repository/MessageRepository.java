package com.consumer2.ConsumerTwo.repository;


import com.consumer2.ConsumerTwo.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface MessageRepository extends JpaRepository<MessageModel,String> {
}
