package com.sithija.travelsearch.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UserInforDto")
public class UserInforDto implements UserDetails {

    @ApiModelProperty(value = "User's id", dataType = "int")
    private int userId;

    @ApiModelProperty(value = "User's name", dataType = "String")
    private String name;

    @ApiModelProperty(value = "User's password", dataType = "String")
    private String password;

    @ApiModelProperty(value = "User's email", dataType = "String")
    private String email;

    @ApiModelProperty(value = "User's address", dataType = "String")
    private String address;

    @ApiModelProperty(value = "User's state", dataType = "String")
    private String state;

    @ApiModelProperty(value = "User's country", dataType = "String")
    private String country;

    @ApiModelProperty(value = "User's role", dataType = "String")
    private String role;

    @ApiModelProperty(value = "User's profile picture")
    private byte[] image;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
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
}
