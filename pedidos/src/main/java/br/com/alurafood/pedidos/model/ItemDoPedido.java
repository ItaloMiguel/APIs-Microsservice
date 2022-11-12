package br.com.alurafood.pedidos.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "item_do_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Integer quantidade;

    private String descricao;

    @ManyToOne(optional = false)
    private Pedido pedido;
}
