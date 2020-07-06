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

import br.com.brasilprev.domain.Produto;
import br.com.brasilprev.repositories.ProdutoRepository;


@RestController
public class ProdutoResource {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping(value="/produtos")
	public List<Produto> all(){
		return produtoRepository.findAll();
	}
	
	@PostMapping(value = "/produtos")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Produto save(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	 }
	
	@GetMapping(value = "/produtos/{produtoId}") 
    public Produto findByCustomerId (@PathVariable Integer produtoId){ 
         return produtoRepository.findById(produtoId).orElseThrow(() -> new ResourceNotFoundException("Produto [produtoId="+produtoId+"] não encontrado"));
    }
	
	@PutMapping(value = "/produtos/{produtoId}")
	public ResponseEntity<Produto> updateProduto(@PathVariable Integer produtoId, @RequestBody Produto novoProduto){
		
		return produtoRepository.findById(produtoId).map(produto ->{
			produto.setDescricao(novoProduto.getDescricao());
			produto.setPreco(novoProduto.getPreco());
			
			produtoRepository.save(produto);
			   return ResponseEntity.ok(produto);
		       }).orElseThrow(() -> new ResourceNotFoundException("Produto [produtoId="+produtoId+"] não encontrado"));

	}
	
	
	@DeleteMapping(value = "/produtos/{produtoId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Integer produtoId){

		return produtoRepository.findById(produtoId).map(produto -> {
			produtoRepository.delete(produto);
		return ResponseEntity.ok().build();
		}
        ).orElseThrow(() -> new ResourceNotFoundException("Produto [produtoId="+produtoId+"] não encontrado"));

	}

}
