package com.securityTest.service;

import com.securityTest.dto.UserInfoDto;
import com.securityTest.reppository.UserInfo;
import com.securityTest.reppository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    /**
     * Spring Security 필수 메소드 구현
     *
     * @param email 이메일
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */
    @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo 로 반환 타입 지정(자동으로 다운 캐스팅됨)
    public UserInfo loadUserByUsername(String email) throws UsernameNotFoundException { // security 에서 지정한 service 이기 때문에 메소드를 필수로 구현
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));
    }

    /**
     * 회원정보 저장
     *
     * @param infoDto 회원정보가 들어있는 DTO
     * @return 저장되는 회원의 PK
     */
    public Long save(UserInfoDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));
        return userRepository.save(UserInfo.builder()
            .email(infoDto.getEmail())
            .auth(infoDto.getAuth())
            .password(infoDto.getPassword()).build()).getCode();
    }
    /*
    회원정보를 저장하는 메소드를 만들기 위해 UserService를 수정합니다. 입력받은 패스워드를 BCrypt로 암호화한 후에
    회원을 저장해주는 메소드를 만들어줍니다.
     */
}
