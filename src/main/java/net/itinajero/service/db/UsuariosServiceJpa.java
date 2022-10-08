package net.itinajero.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import net.itinajero.model.Usuario;
import net.itinajero.repository.UsuariosRepository;
import net.itinajero.service.IUsuariosService;

@Service
@Primary
public class UsuariosServiceJpa implements IUsuariosService{
	@Autowired
	private UsuariosRepository usuariosRepo;
	
	@Override
	public void guardar(Usuario usuario) {
		// TODO Auto-generated method stub
		usuariosRepo.save(usuario);

	}

	@Override
	public void eliminar(Integer idUsuario) {
		// TODO Auto-generated method stub
		usuariosRepo.deleteById(idUsuario);
	}

	@Override
	public List<Usuario> buscarTodos() {
		// TODO Auto-generated method stub
		return usuariosRepo.findAll();
	}



}
