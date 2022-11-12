package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.ItemDoPedido;
import br.com.alurafood.pedidos.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;
    private LocalDateTime dataHora;
    private Status status;
    private List<ItemDoPedido> itens;
}
