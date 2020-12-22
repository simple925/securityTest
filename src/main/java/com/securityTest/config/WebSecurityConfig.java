package com.securityTest.config;

import com.securityTest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@EnableWebSecurity
// Spring Security 를 활성화한다는 의미의 Annotation
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
// WebSecurityConfigurerAdapter 는 Spring Security 의 설정파일로서의 역할을 하기 위해 상속해야 하는 클class
    private final UserService userService;
    // 후에 사용될 유저 정보를 가져올 클래스

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // 비밀번호를 암호화할 때 사용할 인코더를 미리 빈으로 등록해 놓는 과정

    @Override
    public void configure(@NotNull WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }
    /*
    WebSecurityConfigurerAdapter 를 상속받으면 override 할 수 있음 인증을 무시할 경로들을 설정해 놓을 수 있음
        - static 하위 폴더 (css, js, img)는 무조건 접근이 가능해야하기 때문에 인증을 무시해야함
    */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    /*
    WebsecurityConfigurerAdapter 를 상속받으면 override 할 수 있음
        - http 관련 인증 설정이 가능함    
     */
        http
                .authorizeRequests()
                    .antMatchers("/login", "/signup", "/user").permitAll()
                    .antMatchers("/").hasRole("USER")
                    .antMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                .and()
                    .logout()
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
        ;
    }
    /*
    .authorizeRequests() 접근에 대한 인증 설정이 가능함
        - .anyMatchers 를 통해 경로 설정과 권한 설정이 가능함
            - .permitAll(String)    : 누구나 접근이 가능
            - .hasRole(String)      : 특정 권한이 있는 사람만 접근 가능
            - .authenticated()      : 권한이 있으면 무조건 접근 가능
            - .anyRequest() 는 anyMatchers(String) 에서 설정하지 않은 나머지 경로를 의미함
     */
    /*
    .formLogin() 로그인에 관한 설정을 의미함
        - .loginPage(String)         : 로그인 페이지 링크 설정
        - .defaultSuccessUrl(String) : 로그인 성공 후 리다이렉트할 주소
     */
    /*
    .logout() 로그아웃에 관한 설정을 의미함
        - .logoutSuccessUrl(String)       : 로그아웃 성공 후 리다이렉트할 주소
        - .invalidateHttpSession(boolean) : 세션 날리기
     */

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    /*
    로그인할 때 필요한 정보를 가져오는 곳
        - 유저 정보를 가져오는 서비스를 userService 으로 지정함
        - 패스워드 인코더는 아까 빈으로 등록해놓은 passwordEncoder() 를 사용함(BCrypt)
     */
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        // 해당 서비스(userService)에서는 UserDetailService 를 implements 해서 loadUserByUsername() 구현해야함(서비스 참고)
    }
}
