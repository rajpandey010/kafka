package com.consumer3.ConsumerThree.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "datatable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageModel {

    @Id
    private String id;
    private String request;
    private String response;
}


