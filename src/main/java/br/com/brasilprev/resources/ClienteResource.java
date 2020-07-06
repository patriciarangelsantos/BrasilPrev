package br.com.brasilprev.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.rds.model.ResourceNotFoundException;

import br.com.brasilprev.domain.Cliente;
import br.com.brasilprev.repositories.ClienteRepository;


@RestController
public class ClienteResource {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@GetMapping(value="/clientes")
	public List<Cliente> all(){
		return clienteRepository.findAll();
	}
	
	@PostMapping(value = "/clientes")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente save(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	 }
	
	@GetMapping(value = "/clientes/{clienteId}") 
    public Cliente findByClienteId (@PathVariable Integer clienteId){ 
         return clienteRepository.findById(clienteId).orElseThrow(() -> new ResourceNotFoundException("Cliente [clienteId="+clienteId+"] não encontrado"));
    }
	
	@DeleteMapping(value = "/clientes/{clienteId}")
	public ResponseEntity<?> deleteCliente(@PathVariable Integer clienteId){

		return clienteRepository.findById(clienteId).map(cliente -> {
			clienteRepository.delete(cliente);
		return ResponseEntity.ok().build();
		}
        ).orElseThrow(() -> new ResourceNotFoundException("Cliente [clienteId="+clienteId+"] não encontrado"));

	}
	
	@PutMapping(value = "/clientes/{clienteId}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable Integer clienteId,@RequestBody Cliente novoCliente){
		
		return clienteRepository.findById(clienteId).map(cliente -> {
			cliente.setNome(novoCliente.getNome());
			clienteRepository.save(cliente);
			return ResponseEntity.ok(cliente);
		}).orElseThrow(() -> new ResourceNotFoundException("Cliente [clienteId="+clienteId+"] não encontrado"));
		
	}

}
