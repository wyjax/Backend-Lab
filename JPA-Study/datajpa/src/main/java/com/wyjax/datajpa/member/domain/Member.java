package com.wyjax.datajpa.member.domain;

import com.wyjax.datajpa.team.doamin.Team;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // jpa는 기본적으로 default 생성자가 필요한데 public으로 만들면 안되고 protected까지만 필요함
@ToString(of = {"id", "username", "age"}) // 객체를 바로 찍으면 출력, 가급적이면 연관관계 필드는 toString에 포함하지 말자
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private int age;

    // JPA에서는 모든 것을 LAZY로 해야한다. EAGER로 하면 성능 최적화가 어렵다.
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;

        if (Optional.ofNullable(team).isPresent()) {
            this.team = team;
        }
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
        // 객체이기 때문에 반대쪽도 업데이트 해줌
    }
}
