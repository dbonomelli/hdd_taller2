package cl.sarayar.gestorTareasRest.config.auth;

import lombok.Generated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cl.sarayar.gestorTareasRest.entities.Usuario;

@Generated
public class UserDetailsImpl implements UserDetails{

	
	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	
	private Collection<? extends GrantedAuthority> authorities;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	public UserDetailsImpl(Usuario usuario) {
		
		//TODO: Generar privilegios, ya que no es necesario para esta prueba tecnica lo dejamos vacio
		List<GrantedAuthority> authorities = new ArrayList<>();
		this.authorities = authorities;
		this.usuario=  usuario;
	}
	
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	@Override
	public String getPassword() {
		return this.usuario.getClave();
	}

	@Override
	public String getUsername() {
		return this.usuario.getCorreo();
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
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;

		return Objects.equals(this.usuario.getId(), user.getUsuario().getId());
	}
}
