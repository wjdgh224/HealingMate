package apptive.backend.config;

import apptive.backend.handler.login.CustomLoginFailureHandler;
import apptive.backend.handler.login.CustomLoginSuccessHandler;
import apptive.backend.login.jwt.JwtAuthenticationFilter;
import apptive.backend.login.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class MemberConfig {

    private final JwtTokenProvider jwtTokenProvider;

    //비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //특정 경로의 파일 무시

    //HTTP Basic : 모든 엔드포인트 보호
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }*/

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/member/**", "/login");
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        /*httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .headers().frameOptions().disable().and()
                .authorizeHttpRequests()
                .requestMatchers("/login", "/member/**").permitAll()
                .requestMatchers("/post/**").hasAuthority("USER")
                .anyRequest().authenticated();

        httpSecurity
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true);*/

        httpSecurity
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests().requestMatchers("/member/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
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
