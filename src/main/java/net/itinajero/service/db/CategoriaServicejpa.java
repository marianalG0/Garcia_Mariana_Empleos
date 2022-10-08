package net.itinajero.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import net.itinajero.model.Categoria;
import net.itinajero.repository.CategoriasRepository;
import net.itinajero.service.ICategoriasService;

@Service
@Primary //Le decimos a spring que en nuestro controlador, cuando se inyecte una instancia en la variable servicecategorias
		// automaticamente como encontrara 2, le decimos que utilice este
public class CategoriaServicejpa implements ICategoriasService {
	@Autowired
	private CategoriasRepository categoriasRepo;//Con esta sintaxis automaticamente cuando se cree la instancia
	// de nuestra implementacion tambien se va a inyectar una instancia de nuestro repositorio en esta variable, por lo tanto
	// como esta definida al nivel de la clase se podra utilizar en cualquier metodo 
	
	@Override
	public void guardar(Categoria categoria) {
		categoriasRepo.save(categoria);

	}

	@Override
	public List<Categoria> buscarTodas() {
		return categoriasRepo.findAll();//retornamos de nuestro repo lo que nos regrese el metodo findAll//Implementando
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> optional = categoriasRepo.findById(idCategoria);
		//Vamos a preguntar que si de nuestro objeto optional con el metodo, que si nos presenta V si se encontro  
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;// si no esta, se ejecuta el valor nulo
	}

	@Override
	public void eliminar(Integer idCategoria) {
		categoriasRepo.deleteById(idCategoria);
		
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		
		return categoriasRepo.findAll(page);
	}

}
