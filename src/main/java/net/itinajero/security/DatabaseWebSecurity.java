package net.itinajero.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter {
		@Autowired
		private DataSource dataSource;
		
		//BASE DE DATOS POR DEFECTO
		/*@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(dataSource);
		}*/
		
		//BASE DE DATOS personalizados
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username, password, estatus from Usuarios where username=?")
		.authoritiesByUsernameQuery("select u.username, p.perfil from UsuarioPerfil up " +
		"inner join Usuarios u on u.id = up.idUsuario " +
		"inner join Perfiles p on p.id = up.idPerfil " +
		"where u.username = ?");
		}
}//Recuperando user y contraseñas en la base de datos
