package sparkminds.library.models;

import java.util.Collection;
import java.util.List;
import lombok.Data;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sparkminds.library.enums.AccountStatus;
import sparkminds.library.enums.Gender;
import sparkminds.library.enums.Role;
@Data
public class CustomUserDetails implements UserDetails {

    private String email;

    private String password;

    private AccountStatus accountStatus;

    private List<GrantedAuthority> authorities;

    private String jti;

    public CustomUserDetails(String email, String password, List<GrantedAuthority> authorities, AccountStatus accountStatus) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.accountStatus = accountStatus;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountStatus != AccountStatus.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {
        return accountStatus == AccountStatus.ACTIVE;
    }

}
