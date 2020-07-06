package br.com.brasilprev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.brasilprev.domain.ItemPedido;


@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer>{
	
	@Query("select ip from ItemPedido ip where ip.id.pedido.id = :pedidoId and ip.id.produto.id = :produtoId" )
	ItemPedido findByIdPedidoAndProduto(@Param("pedidoId") Integer pedidoId, @Param("produtoId") Integer produtoId);
		
}
