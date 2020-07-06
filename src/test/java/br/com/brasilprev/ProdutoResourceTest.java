package br.com.brasilprev;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;

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

import br.com.brasilprev.domain.Produto;
import br.com.brasilprev.resources.ProdutoResource;

@RunWith(SpringRunner.class)
@WebMvcTest(ProdutoResource.class)
public class ProdutoResourceTest {
	
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
	public void getIProdutoList() throws Exception {
		String uri = "/produtos";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Produto[] produtos = mapFromJson(content, Produto[].class);
		assertTrue(produtos.length > 0);
	}
	
	@Test
	public void testUpdateProdutos() throws Exception {
	   String uri = "/produtos/1";
	   
	   Produto produto = new Produto();
	   produto.setDescricao("Nome do Produto");
	   produto.setPreco(new BigDecimal(1));
	   
	   
	   String inputJson = mapToJson(produto);
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   assertEquals(content, "Produto foi atualizado com sucesso");
	}
	
	@Test
	public void testSaveProduto() throws Exception {
		String uri = "/produtos";
		Produto produto = new Produto();
		produto.setDescricao("Nome do Produto");
		produto.setPreco(new BigDecimal(1));

		String inputJson = mapToJson(produto);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Produto salvo com sucesso");
	}
	
	@Test
	public void testDeleteProduto() throws Exception {
		String uri = "/produtos/1";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Produto deletado com sucesso");
	}

}
