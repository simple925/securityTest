package com.securityTest.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/**
 * 메세지 관련 Config file
 */
@Configuration
public class SecurityMessageConfig {

    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.KOREA); // 위치 한국으로 설정
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8"); // encoding config
        messageSource.setBasenames("classpath:message/security_message", "classpath:org/springframework/security/messages");
        // 커스텀한 properties file, security properties file 순서대로 설정
        return messageSource;
        /*
        MessageSource를 반환해주는 messageSource() method 를 만들어줍니다.
        만약 한국어가 아닌 다른 언어를 지정하고 싶으면 Locale.KOREA 를 다른 위치로 변경하면 됩니다.
        그 후에 ReloadableResourceBundleMessageSource 를 설정해주면 되는데,
        인코딩을 UTF-8로 설정해주고, message 관련 properties 파일이 있는 위치를 지정해주어야합니다.
        저는 메세지를 제가 커스텀 한 설정 파일에서 찾고, 없으면 기본 설정 파일에서 가져오게 하겠습니다.
        setBasenames()으로 커스텀 할 설정 파일의 위치를 먼저 지정해주고(아직 안만듬), 에러 메세지가 기본
        적으로 정의되어있는 기본 설정파일의 위치인 classpath:org/springframework/security/messages 를
        지정해줬습니다.
         */
    }
}
