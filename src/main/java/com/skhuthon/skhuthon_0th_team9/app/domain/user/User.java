package com.skhuthon.skhuthon_0th_team9.app.domain.user;

import com.skhuthon.skhuthon_0th_team9.app.domain.review.Review;
import com.skhuthon.skhuthon_0th_team9.app.domain.user.social.SocialConnection;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends UserBase{

    @Column(name = "USER_NICKNAME")
    private String nickName;

    // 추후 추가할 컬럼이 있다면 추가
    // 컬럼 추가하면 OAuthDto에서 LoginRequest에 추가, login에서 builder 사용할때도 추가

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SocialConnection> socialConnectionSet = new HashSet<>();

    @Builder
    public User(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.userAccessLevel = UserAccessLevel.UNREGISTERED_USER;
    }

    public void addSocialConnection(SocialConnection socialConnection) {
        this.socialConnectionSet.add(socialConnection);
    }

    public String[] getSocialTypes() {
        return socialConnectionSet.stream()
                .map(SocialConnection::getSocialType)
                .map(Enum::name)
                .toArray(String[]::new);
    }
}
