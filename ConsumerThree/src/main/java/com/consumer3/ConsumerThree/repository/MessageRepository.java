package com.consumer3.ConsumerThree.repository;

import com.consumer3.ConsumerThree.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface MessageRepository extends JpaRepository<MessageModel,String> {
}
