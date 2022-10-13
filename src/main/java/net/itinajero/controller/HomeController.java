package net.itinajero.controller;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	
	//Mapeando el login
	@GetMapping("/login" )
	public String mostrarLogin() {
	return "formLogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request){
	SecurityContextLogoutHandler logoutHandler =
	new SecurityContextLogoutHandler();
	logoutHandler.logout(request, null, null);
	return "redirect:/";
	}
	
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody //Al hacer una peticion a este metodo, en vez de buscar una lista, regresara el texto directamente al navegador de inter 
	public String encriptar(@PathVariable("texto") String texto) {
		return texto = "Encriptado en Bcrypt: " + passwordEncoder.encode(texto);
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
	
	//Metodo que renderiza al /, es decir al directorio raiz de la aplicación
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		String username = auth.getName();//Recupera el nombre del usuario
		System.out.println("Nombre del usuario : " + username);
		
		//Vamos a recorrer nuestra colección de objectos de tipo GrantedAuthority
		for(GrantedAuthority rol: auth.getAuthorities() ) {
			System.out.println("ROL: " + rol.getAuthority()); //Regresamos el nombre del rol

		}
		
		//Le diremos que si el atributo usuario no existe, lo vamos a crear
		if(session.getAttribute("usuario") == null) {
			
			//Recuperando el objeto usuario 
			Usuario usuario = serviceUsuarios.buscarPorUsername(username); 	
			//De esta forma no almacenamos la contraseña en la session
			usuario.setPassword(null);
			//Solo para saber si el objeto fue encontrado, desplegando en consola
			System.out.println("Usuario : " + usuario);
			session.setAttribute("usuario", usuario);//Almacenando datos en la sesion del usuario
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario,Model model) {
		return "usuarios/formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		 
		String pwdPlano = usuario.getPassword(); //Recuperando el password en texto plano
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);//Guarda contraseña encriptada
		usuario.setPassword(pwdEncriptado);
		
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
	public String buscar(@ModelAttribute("search") vacante vacante,Model model) {//Agregamos modelAttribute con el parametro con el nombre del atributo con el que se hara el databinding
		System.out.println("Buscando por :" + vacante);
		
		//where descripcion like '%?%'
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());//Para cambiar la condicion y use operador like
		
		//Declarando variable de tipo example que reciba una muestra de tipo vacante
		Example<vacante> example = Example.of(vacante, matcher);
		//Declarar variable para guardar resultado
		List<vacante> lista = serviceVacantes.buscarByExample(example);
		//Agregando al modelo la lista para que se renderice en la pagina princípal 
		model.addAttribute("vacantes",lista);
		
		
		return "home";
	}
	
	/*
	 * @InitBinder para strings si los detecta vacios en el Data Binding los settea a NULL
	 * @paramBinder
	 * */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));//Basicamente le decimos para los tipos de datos string vas a registrar
				//un editor o modificador para este tipo de dato de tipo stringTrimmerEditor, si recibe true lo transforma a null
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
