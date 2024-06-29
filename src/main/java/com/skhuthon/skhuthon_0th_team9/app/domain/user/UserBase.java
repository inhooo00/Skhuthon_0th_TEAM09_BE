package com.skhuthon.skhuthon_0th_team9.app.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.skhuthon.skhuthon_0th_team9.app.domain.user.UserAccessLevel.UNREGISTERED_USER;
import static com.skhuthon.skhuthon_0th_team9.app.domain.user.UserAccessLevel.USER;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED) // 이를 통해서 부모 엔티티 속성과 메서드를 자식 엔티티에게 상속받을 수 있음
                                                // 상속관계가 있는 엔티티를 효율적으로 관리하고 코드의 중복을 방지할 수 있지만
                                                // 성능 문제가 발생할 수 있음, 상속 관계에 따라서 쿼리의 복잡도가 증가할 수 있음
public class UserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    // Inheritance를 통해서 자식 클래스가 해당 속성에 접근하기 위해서 필요한 컬럼은 Protected로 선언
    @Column(name = "USER_EMAIL", unique = true)
    protected String email;

    protected String password;

    @Enumerated(EnumType.STRING)
    protected UserAccessLevel userAccessLevel;

    public void certify() {
        if (this.userAccessLevel == UNREGISTERED_USER) {
            this.userAccessLevel = USER;
        }
    }
}
