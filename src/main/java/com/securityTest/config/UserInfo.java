package com.securityTest.config;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class UserInfo implements UserDetails {
    @Id
    @Column(name = "code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "auth")
    private String auth;

    @Builder
    public UserInfo(String email, String password, String auth) {
        this.email = email;
        this.password = password;
        this.auth = auth;
    }
    // 사용자의 권한을 콜렉션 형태로 반환
    // 단, 클래스 자료형은 GrantedAuthority 를 구현해야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")){
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 사용자의 id를 반환 (unique한 값)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자의 password를 반환
    @Override
    public String getPassword() {
        return password;
    }
    
    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않음
    }
    
    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }
    
    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }
    
    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
    /*
    여기서 추가로 설명할 부분은 getAuthorities() 인데, 이 메소드는 사용자의 권한을 콜렉션 형태로 반환해야하고, 콜렉션의
    자료형은 무조건적으로 GrantedAuthority 를 구현해야합니다. 저는 권한이 중복되면 안 되기 때문에 Set<GrantedAuthority>
    을 사용했습니다.
    ADMIN 은 관리자의 권한(ADMIN)뿐만 아니라 일반 유저(USER)의 권한도 가지고 있기 때문에, ADMIN 의 auth 는
    "ROLE_ADMIN,ROLE_USER"와 같은 형태로 전달이 될 것이고, 쉼표(,) 기준으로 잘라서 ROLE_ADMIN 과 ROLE_USER 를
    roles 에 추가해줍니다. 아까 루트 패스("/")에 권한을 USER 에게만 주었지만, ADMIN 은 두 개의 권한(USER, ADMIN)을
    가지고 있기 때문에 접근이 가능합니다.
     */
}
