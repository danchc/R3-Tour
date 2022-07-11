package it.uniroma3.siw.r3tour.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User, UserDetails {

    private OAuth2User oauth2User;

    public CustomOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return oauth2User.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //per ottenere lo username
    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    //per ottenere la e-mail
    public String getEmail() {
        return oauth2User.<String>getAttribute("email");
    }
}
