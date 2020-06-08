
package br.com.markercode.restClient;




import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;





@RegisterRestClient(configKey="pagamento-api")
public interface PagamentoRestClient {

    @POST
    @Path("/processar-pagamento/{id}/{valor}/")
    @Produces(MediaType.APPLICATION_JSON)
    void pagar(@PathParam("id") Long idCliente,@PathParam("valor") Double valor);
    
}