package br.com.markercode.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.markercode.model.Compra;
import br.com.markercode.model.Produto;
import br.com.markercode.restClient.PagamentoRestClient;
import br.com.markercode.restClient.ProdutoRestClient;

@RequestScoped 
public class CompraService {

    @RestClient
    @Inject
    ProdutoRestClient produtoRestClient;

    @RestClient
    @Inject
    PagamentoRestClient pagamentoRestClient;

    private static final Logger LOG = Logger.getLogger(CompraService.class);

    @Traced
    @Transactional
    public void efetuarCompra(Long idCliente, List<Produto> produtos) {
       LOG.info("INICIANDO TRANSAÇÃO de compra");
        persistirCompra(idCliente, produtos);

        try {
             atualizarEstoque(produtos);
             LOG.info("Estoque atualizado");
             processarPagamento(idCliente, produtos);
             LOG.info("Pagamento realizado");
            
        } catch (Exception e) {
            e.printStackTrace();
            somarEstoque(produtos);
            LOG.info("Erro na compra. estque atualizado");
           
        }
       
        
    }

    private void somarEstoque(List<Produto> produtos) {
         produtoRestClient.somarEstoque(produtos);

	}

	public void processarPagamento(Long idCliente, List<Produto> produtos){

        var valor = produtos.stream().reduce(0.0, (subtotal, produto) -> subtotal + (produto.getQuantidade() * produto.getValor()), Double::sum);

        pagamentoRestClient.pagar(idCliente, valor);
    }


    private void atualizarEstoque(List<Produto> produtos) {

        produtoRestClient.atualizarEstoque(produtos);
	}

	public void persistirCompra(Long idCliente, List<Produto> produtos){
        Compra compra = new Compra();

        compra.data = new Date();

        compra.idCliente = idCliente;

        compra.idProdutos = produtos.stream().map(produto -> produto.getId()).collect(Collectors.toList());

        compra.valor = produtos.stream().reduce(0.0, (subtotal, produto) -> subtotal + (produto.getQuantidade() * produto.getValor()), Double::sum);

        compra.persist();
    }

    
}