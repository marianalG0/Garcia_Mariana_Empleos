package net.itinajero.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.itinajero.model.vacante;
import net.itinajero.repository.vacantesRepository;
import net.itinajero.service.IVacantesServices;

@Service //Clase de servicio
@Primary
public class VacantesServicejpa implements IVacantesServices {

	@Autowired //Para inyectar la instancia
	private vacantesRepository vacantesrepo;
	
	//AGREGADOS LOS 3 METODOS DECLARADOS EN LA INTERFAZ
	@Override
	public List<vacante> buscarTodas() {
		// Regresamos todas las vacantes 
		
		return vacantesrepo.findAll();
	}

	@Override
	public vacante buscarPorId(Integer idVacante) {
		// llamamos el repo y utilizando el metodo, le psamos como param id
		Optional<vacante>  optional = vacantesrepo.findById(idVacante);
		if(optional.isPresent()) {
			return optional.get(); //retornamos lo que regrese el metodo get
		}
		return null;//retornamos nulo si no se encuentra
	}

	@Override
	public void guardar(vacante vacante) {
		vacantesrepo.save(vacante);

	}

	@Override
	public List<vacante> buscarDestacadas() {
		
		return vacantesrepo.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
	}

	@Override
	public void eliminar(Integer idVacante) {
		vacantesrepo.deleteById(idVacante);
		
	}

	@Override
	public List<vacante> buscarByExample(Example<vacante> example) {
		// Implementacion del metodo
		
		//Retornar del repositorio con el metodo 
		return vacantesrepo.findAll(example);
	}

	@Override
	public Page<vacante> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return vacantesrepo.findAll(page);
	}

}
