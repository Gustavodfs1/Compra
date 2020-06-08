package br.com.markercode.model;




import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "tb_compra")
public class Compra extends PanacheEntity{
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date data;

    @Column(nullable = false)
    public Long idCliente;

    @ElementCollection
    @CollectionTable(name="tb_produtos_comprados", joinColumns=@JoinColumn(name="id_produtos"))
    public List<Long> idProdutos;

    @Column(nullable = false)
    public Double valor;
}