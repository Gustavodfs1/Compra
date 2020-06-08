package br.com.markercode.restClient;

import java.util.List;



import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


import br.com.markercode.model.Produto;




@RegisterRestClient(configKey="produto-api")
public interface ProdutoRestClient {

    @POST
    @Path("/atualizar-estoque")
    @Produces(MediaType.APPLICATION_JSON)
    void atualizarEstoque(List<Produto> produtos);

    @POST
    @Path("/somar-estoque")
    @Produces(MediaType.APPLICATION_JSON)
	void somarEstoque(List<Produto> produtos);
    
}