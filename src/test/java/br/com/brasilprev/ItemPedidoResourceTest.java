package br.com.brasilprev;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

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

import br.com.brasilprev.domain.ItemPedido;
import br.com.brasilprev.resources.ItemPedidoResource;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemPedidoResource.class)
public class ItemPedidoResourceTest {
	
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
	public void getItensPedidoList() throws Exception {
		String uri = "/itemPedidos";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		ItemPedido[] itemPedidos = mapFromJson(content, ItemPedido[].class);
		assertTrue(itemPedidos.length > 0);
	}
	
	@Test
	public void updateItemPedidos() throws Exception {
	   String uri = "/itemPedidos/1/produto/1";
	   ItemPedido itemPedido = new ItemPedido();
	   itemPedido.setDesconto(new Double(0));
	   itemPedido.setQuantidade(new Integer(1));
	   itemPedido.setValorPedido(new Double(1));
	   
	   
	   String inputJson = mapToJson(itemPedido);
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   assertEquals(content, "Itens Pedido foram atualizados com sucesso");
	}

}
