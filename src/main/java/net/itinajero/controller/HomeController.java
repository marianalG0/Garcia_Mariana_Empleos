package net.itinajero.controller;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.model.Perfil;
import net.itinajero.model.Usuario;
import net.itinajero.model.vacante;
import net.itinajero.service.ICategoriasService;
import net.itinajero.service.IUsuariosService;
import net.itinajero.service.IVacantesServices;

@Controller
public class HomeController {
	
	@Autowired
	private IVacantesServices serviceVacantes;
	
	@Autowired
    private IUsuariosService serviceUsuarios;
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes",lista);
		
		return "tabla";
	}//Renderiza la lista de objetos de tipo vacante
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model){
		vacante vacante = new vacante();
		vacante.setNombre("Ingeniero de Comunicaciones");
		vacante.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		
		//Agregando al modelo
		model.addAttribute("vacante",vacante);
		
		return "detalle";
	}
	
	
	
	@GetMapping("/listado")
	public String mostrarListado(Model model) {
		List<String> lista = new LinkedList<String>();//Creador de la lista
		//Valores de la lista
		lista.add("Ingeniero en Sistemas");
		lista.add("Auxiliar de Contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		
		//Agregando la lista al modelo
		model.addAttribute("empleos", lista);
		
		return "listado";
	}
	
	//Encargado de renderizar la pagina principal
	@GetMapping("/") 
	public String mostrarHome(Model model) {
		//agregando datos al modelo
		/*model.addAttribute("mensaje", "Bienvenidos a Empleos App");
		model.addAttribute("fecha", new Date()); */
		
		/*//vareables 
		String nombre = "Auxiliat de Contabilidad";
		Date fechaPub = new Date();
		double salario = 9000.0;
		boolean vigente = true;
		//Agregandolos al modelo ↑
		
		model.addAttribute("nombre", nombre);
		model.addAttribute("fecha", fechaPub);
		model.addAttribute("salario", salario);
		model.addAttribute("vigente", vigente);
		*/
		
		/*YA NO ES NECESARIO ESTAS DOS LINEAS */
		//List<vacante> lista = serviceVacantes.buscarTodas();//estamos recuperando la lista de vacantes, utilizando nuestra clase de servicio 
		//model.addAttribute("vacantes",lista);//Agregando la lista al modelo 
		
		
		return "home";
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario,Model model) {
		return "usuarios/formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		 //Ejercicio.
		 usuario.setEstatus(1);//Activo por defecto
		 usuario.setFechaRegistro(new Date());//Fecha de registro, la actual
		 
		 Perfil perfil = new Perfil();
		 perfil.setId(3);//PERFIL USUARIO
		 usuario.agregar(perfil);
		 
		 /*Guardamos el usuario en la base de datos*/
		serviceUsuarios.guardar(usuario);
		attributes.addFlashAttribute("msg", "El registro fue guardado exitosamente");		
		return "redirect:/usuarios/index";
	}
	
	//Metodo 
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") vacante vacante) {//Agregamos modelAttribute con el parametro con el nombre del atributo con el que se hara el databinding
		return "home";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		vacante vacanteSearch = new vacante();
		vacanteSearch.reset();
		model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
		
		//Listado de categorias
		model.addAttribute("categorias", serviceCategorias.buscarTodas());

		model.addAttribute("search", vacanteSearch);
	}
	
}
