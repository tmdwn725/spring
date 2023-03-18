package com.example.demo.club.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat")
public class Chat {
}
