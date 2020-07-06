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

import br.com.brasilprev.domain.Cliente;
import br.com.brasilprev.resources.ClienteResource;

@RunWith(SpringRunner.class)
@WebMvcTest(ClienteResource.class)
public class ClienteResourceTest {
	
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
	public void createCliente() throws Exception {
		String uri = "/clientes";
		Cliente cliente = new Cliente();
		cliente.setId(3);
		cliente.setNome("Teste teste");

		String inputJson = mapToJson(cliente);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Cliente salvo com sucesso");
	}


	@Test
	public void getClientesList() throws Exception {
		String uri = "/clientes";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Cliente[] clientes = mapFromJson(content, Cliente[].class);
		assertTrue(clientes.length > 0);
	}

	@Test
	public void testUpdateCliente() throws Exception {

		String uri = "/clientes/1";	
		Cliente cliente = new Cliente();
		cliente.setNome("Teste teste");

		String inputJson = mapToJson(cliente);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Cliente atualizado com sucesso");
	}

	@Test
	public void testDeleteCliente() throws Exception {
		String uri = "/clientes/1";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Cliente deletado com sucesso");
	}
	

}
