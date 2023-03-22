package apptive.backend.login.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class LoginDetail implements UserDetails {

    String ROLE_PREFIX = "ROLE_";

    private String memberNickname;
    private String pwd;
    private String memberRole;

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return memberNickname;
    }

    // 계정이 만료되지 않았는지 리턴 (true : 만료 안 됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않은지 리턴 (true : 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 (true : 만료 안 됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능)인지 리턴 (true : 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정이 갖고 있는 권한 목록 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
        collectors.add(new SimpleGrantedAuthority(ROLE_PREFIX + memberRole));
        return collectors;
    }
}
