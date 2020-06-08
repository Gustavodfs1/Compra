package br.com.markercode.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.markercode.model.Produto;
import br.com.markercode.service.CompraService;

@Path("/compra")
public class CompraController {

    @Inject
    CompraService compraService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response efetuarCompra(@PathParam("id") Long id, List<Produto> produtos) {

        compraService.efetuarCompra(id, produtos);



        return Response.ok().build();
    }

}