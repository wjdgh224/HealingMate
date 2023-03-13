package apptive.backend.config;

import apptive.backend.handler.login.CustomLoginFailureHandler;
import apptive.backend.handler.login.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class MemberConfig {

    //비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //특정 경로의 파일 무시

    //HTTP Basic : 모든 엔드포인트 보호
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/member/join/1", "/member/join/2");
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        //접근 권한
        http.authorizeHttpRequests()
                .requestMatchers("/", "/member/join/1", "/member/join/2", "/login").permitAll()
                .anyRequest().authenticated();

        //폼 로그인
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureHandler(failureHandler())
                .successHandler(successHandler())
                .permitAll()
                .and()
                .csrf().disable();

        //로그아웃
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("SESSION", "JSESSIONID");

        //중복 로그인
        http.sessionManagement()
                .invalidSessionUrl("/login?invalidSession")
                .sessionAuthenticationErrorUrl("/login?maximumSessions")
                .maximumSessions(1) // 최대 허용 세션 수
                .maxSessionsPreventsLogin(false) // 중복 로그인하면 기존 세션 만료
                .expiredUrl("/login?expiredSession");

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)  throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler successHandler = new CustomLoginSuccessHandler();
        successHandler.setDefaultTargetUrl("/");

        return successHandler;
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new CustomLoginFailureHandler();
    }
}
