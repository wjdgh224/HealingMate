package apptive.backend.login.provider;

import apptive.backend.login.domain.Member;
import apptive.backend.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {

    private MessageSourceAware messages;

    @Autowired
    private LoginService loginService;

    private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = (MessageSourceAware) new MessageSourceAccessor(messageSource);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        //비밀번호 확인
        Member member = loginService.authenticate(username, password);

        //예외 발생 여부 확인
        preAuthenticationChecks(member);

        return new UsernamePasswordAuthenticationToken(username, password, authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void preAuthenticationChecks(Member memberDetail) {
        try {
            userDetailsChecker.check((UserDetails) memberDetail);
        } catch (UsernameNotFoundException e) {
            log.info("계정 없음");
            throw e;
        }
    }
}
