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
import br.com.brasilprev.domain.Pedido;
import br.com.brasilprev.repositories.ClienteRepository;
import br.com.brasilprev.repositories.PedidoRepository;

@RestController
public class PedidoResource {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@PostMapping(value = "/clientes/{clienteId}/pedido")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Pedido save(@PathVariable Integer clienteId,@RequestBody Pedido pedido) {
		return clienteRepository.findById(clienteId).map(cliente -> {
			pedido.setCliente(cliente);
			return pedidoRepository.save(pedido);
			
		}).orElseThrow(() -> new ResourceNotFoundException("Cliente [clienteId="+clienteId+"] não encontrado"));

	 }
	
	@GetMapping(value = "/clientes/{clienteId}/pedidos") 
	 public List<Pedido> all (@PathVariable Integer clienteId){ 
              return pedidoRepository.findByCliente_Id(clienteId);
	 } 
	
	@GetMapping(value = "/pedidos")
	 public List<Pedido> allPedidos(){
		return pedidoRepository.findAll();
	}
	
	@DeleteMapping(value = "/cliente/{clienteId}/pedido/{pedidoId}")
	public ResponseEntity<?> deletePedido(@PathVariable Integer clienteId,@PathVariable Integer pedidoId){

		if (!clienteRepository.existsById(clienteId)) {
			throw new ResourceNotFoundException("Cliente [clienteId="+clienteId+"] não encontrado");
		}
		
		return pedidoRepository.findById(pedidoId).map(pedido ->{
			pedidoRepository.delete(pedido);
			   return ResponseEntity.ok().build();
		       }).orElseThrow(() -> new ResourceNotFoundException("Pedido [pedidoId="+pedidoId+"] não encontrado"));

	}
	
	@PutMapping(value = "/clientes/{clienteId}/pedidos/{pedidoId}")
	public ResponseEntity<Pedido> updatePedido(@PathVariable Integer clienteId,@PathVariable Integer pedidoId,@RequestBody Pedido novoPedido){

		Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ResourceNotFoundException("Cliente [clienteId="+clienteId+"] não encontrado"));
		
		return pedidoRepository.findById(pedidoId).map(pedido ->{
			pedido.setCliente(cliente);
			pedido.setDataPedido(novoPedido.getDataPedido());
			pedido.setTotalPedido(novoPedido.getTotalPedido());
			
			pedidoRepository.save(pedido);
			   return ResponseEntity.ok(pedido);
		       }).orElseThrow(() -> new ResourceNotFoundException("Pedido [pedidoId="+pedidoId+"] não encontrado"));

		
	}

}
