package br.com.controledecontas.serializer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.controledecontas.model.Usuario;

public class JSONSerializerTest {
	
	private JSONSerializer serializer;
	
	private static final String JSON_NAO_IDENTADO_SEM_ROOT = "{\"id\": 1,\"nome\": \"Renan\",\"login\": " +
			"\"renanigt\",\"senha\": \"teste\",\"saldo\": \"0.00\"}";
	
	private static final String JSON_IDENTADO_COM_ROOT = "{\"usuario\": {\n  \"id\": 1,\n  \"nome\": \"Renan\",\n  \"login\": " +
			"\"renanigt\",\n  \"senha\": \"teste\",\n  \"saldo\": \"0.00\"\n}}";

	private static final String JSON_SEM_ALIAS_IDENTADO_COM_ROOT = "{\"br.com.controledecontas.model.Usuario\": {\n  \"id\": 1,\n  \"nome\": \"Renan\",\n  \"login\": " +
			"\"renanigt\",\n  \"senha\": \"teste\",\n  \"saldo\": \"0.00\"\n}}";
	
	@Test
	public void deveriaSerializarNaoIdentadoSemRoot() {
		serializer = new JSONSerializer();
		String json = serializer.withoutRoot().serialize("usuario", usuario());
		
		assertEquals(JSON_NAO_IDENTADO_SEM_ROOT, json);
	}

	@Test
	public void deveriaSerializarIdentadoComRoot() {
		serializer = new JSONSerializer();
		String json = serializer.indented().serialize("usuario", usuario());
		
		assertEquals(JSON_IDENTADO_COM_ROOT, json);
	}

	@Test
	public void deveriaSerializarSemAliasIdentadoComRoot() {
		serializer = new JSONSerializer();
		String json = serializer.indented().serialize(usuario());
		
		assertEquals(JSON_SEM_ALIAS_IDENTADO_COM_ROOT, json);
	}
	
	private Usuario usuario() {
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setNome("Renan");
		usuario.setLogin("renanigt");
		usuario.setSenha("teste");
		
		return usuario;
	}
	
}
