package br.com.brasilprev.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.brasilprev.domain.ItemPedido;
import br.com.brasilprev.domain.ItemPedidoPK;
import br.com.brasilprev.domain.Pedido;
import br.com.brasilprev.domain.Produto;
import br.com.brasilprev.repositories.ItemPedidoRepository;

@RestController
public class ItemPedidoResource {
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	@GetMapping(value = "/itemPedidos/{pedidoId}/produto/{produtoId}") 
    public ItemPedido findByIdPedidoAndProduto (@PathVariable Integer pedidoId,@PathVariable Integer produtoId){ 
         return itemPedidoRepository.findByIdPedidoAndProduto(pedidoId, produtoId);
	}    		 
	
	@PostMapping(value = "/itemPedidos/{pedidoId}/produto/{produtoId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ItemPedido save(@RequestBody ItemPedido itemPedido, @PathVariable Integer pedidoId, @PathVariable Integer produtoId) {
		ItemPedidoPK pk = new ItemPedidoPK();
		Pedido novoPedido = new Pedido();
		Produto novoProduto = new Produto();
		
		novoPedido.setId(pedidoId);
		novoProduto.setId(produtoId);
		pk.setPedido(novoPedido);
		pk.setProduto(novoProduto);
		
		itemPedido.setId(pk);
		
		return itemPedidoRepository.save(itemPedido);
	 }
	

}
