package br.com.brasilprev.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.brasilprev.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	
	List<Pedido> findByCliente_Id(Integer idCliente);

}
