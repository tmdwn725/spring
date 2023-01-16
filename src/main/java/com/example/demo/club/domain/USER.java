package com.example.demo.club.domain;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Table(name = "users")
public class USER {

    @Id
    Long id;

}
