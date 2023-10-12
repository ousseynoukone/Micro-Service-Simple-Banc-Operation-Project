package com.rezilux.auth_services.Models;


import jakarta.persistence.*;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table()
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name = "last_name" , nullable = false)
    private String lastName;
    @Column(name = "first_name" , nullable = false)

    private String firstName;
    @Column( nullable = false)

    private String login;
    @Column( nullable = false)

    private String password;
}
