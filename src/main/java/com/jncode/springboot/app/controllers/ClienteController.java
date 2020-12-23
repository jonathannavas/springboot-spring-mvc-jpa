package com.jncode.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jncode.springboot.app.models.entity.Cliente;
import com.jncode.springboot.app.models.service.IClienteService;
import com.jncode.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/listar")
	public String listar(@RequestParam(name = "page",defaultValue = "0") int page,Model model) {
		
		//paginador
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar",clientes);
		
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes );
		model.addAttribute("page", pageRender);
		
		return "listar";
	}
	
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		
		Cliente cliente = new Cliente();
		
		model.put("titulo", "Formulario de cliente");
		model.put("cliente",cliente);		
		
		return "form";
	}
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(name = "id") Long id, Map<String,Object> model, RedirectAttributes flash) {
		
		Cliente cliente = null;
		
		if(id>0) {
			
			cliente = clienteService.findOne(id);
			
			if(cliente == null) {
				flash.addFlashAttribute("error", "Error!, El cliente no existe");
				return "redirect:/listar";
			}
			
		}else {
			flash.addFlashAttribute("error", "Error!, el id del cliente no puede ser 0");
			return "redirect:/listar";
			
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Editar cliente");
		
		return "form";
	}
	
	
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		
		String mensaje = (cliente.getId() != null) ? "Cliente editado con exito" : "Cliente creado con exito";
		
		clienteService.Save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		
		return "redirect:listar";
	}
	
	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		
		if(id>0) {
			clienteService.detete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito");
		}
		
		return "redirect:/listar";
	}
	
}
