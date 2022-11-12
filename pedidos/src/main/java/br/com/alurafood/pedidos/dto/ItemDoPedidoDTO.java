package br.com.alurafood.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDoPedidoDTO {
    private Long id;
    private Integer quantidade;
    private String descricao;
}
