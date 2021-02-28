package com.wyjax.jpadatastudy.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Women {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "man_id")
    private Man man;
}
