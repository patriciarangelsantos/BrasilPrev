package br.com.brasilprev;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.brasilprev.domain.Cliente;
import br.com.brasilprev.domain.Pedido;
import br.com.brasilprev.resources.PedidoResource;


@RunWith(SpringRunner.class)
@WebMvcTest(PedidoResource.class)
public class PedidoResourceTest {
	
		@Autowired
		private MockMvc mockMvc;
		
		
		protected String mapToJson(Object obj) throws JsonProcessingException {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(obj);
		}
		
		protected <T> T mapFromJson(String json, Class<T> clazz)throws JsonParseException, JsonMappingException, IOException {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(json, clazz);
		}
		
		@Test
		public void testSavePedido() throws Exception {
			String uri = "/clientes/3/pedido";
			Pedido pedido = new Pedido();
			pedido.setId(3);
			pedido.setCliente(new Cliente());
			pedido.setDataPedido(new Date());
			pedido.setTotalPedido(new BigDecimal(0));

			String inputJson = mapToJson(pedido);
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

			int status = mvcResult.getResponse().getStatus();
			assertEquals(201, status);
			String content = mvcResult.getResponse().getContentAsString();
			assertEquals(content, "Pedido salvo com sucesso");
		}


		@Test
		public void getPedidosList() throws Exception {
			String uri = "/pedidos";
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

			int status = mvcResult.getResponse().getStatus();
			assertEquals(200, status);
			String content = mvcResult.getResponse().getContentAsString();
			Pedido[] pedidos = mapFromJson(content, Pedido[].class);
			assertTrue(pedidos.length > 0);
		}

		@Test
		public void testUpdatePedido() throws Exception {

			String uri = "/clientes/1/pedidos/1";	
			Pedido pedido = new Pedido();
			pedido.setDataPedido(new Date());
			pedido.setTotalPedido(new BigDecimal(1));
			pedido.setCliente(new Cliente());

			String inputJson = mapToJson(pedido);
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

			int status = mvcResult.getResponse().getStatus();
			assertEquals(200, status);
			String content = mvcResult.getResponse().getContentAsString();
			assertEquals(content, "Pedido atualizado com sucesso");
		}

		@Test
		public void testDeletePedido() throws Exception {
			String uri = "/clientes/1/pedidos/1";
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
			int status = mvcResult.getResponse().getStatus();
			assertEquals(200, status);
			String content = mvcResult.getResponse().getContentAsString();
			assertEquals(content, "Pedido deletado com sucesso");
		}
}
