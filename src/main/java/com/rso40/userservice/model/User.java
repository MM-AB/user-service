package com.rso40.userservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "user")
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    
}
