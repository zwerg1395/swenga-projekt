package at.fhj.ima.employee.employeemanager.security

import at.fhj.ima.employee.employeemanager.entity.User
import at.fhj.ima.employee.employeemanager.entity.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class MyUserPrincipal(val user: User) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = HashSet<GrantedAuthority>()
        if (user.role == UserRole.ROLE_ADMIN) {
            authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
        }
        authorities.add(SimpleGrantedAuthority("ROLE_USER"))
        return authorities
    }

    override fun isEnabled(): Boolean {
        return true;
    }

    override fun getUsername(): String {
        return user.username.orEmpty();
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true;
    }

    override fun getPassword(): String {
        return user.password.orEmpty();
    }

    override fun isAccountNonExpired(): Boolean {
        return true;
    }

    override fun isAccountNonLocked(): Boolean {
        return true;
    }

}
