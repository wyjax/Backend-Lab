package com.wyjax.jpadatastudy.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Man {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "man")
    private Women women;
}
