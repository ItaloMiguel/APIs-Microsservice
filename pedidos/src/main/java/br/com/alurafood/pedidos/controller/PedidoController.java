package br.com.alurafood.pedidos.controller;

import br.com.alurafood.pedidos.dto.PedidoDTO;
import br.com.alurafood.pedidos.dto.StatusDTO;
import br.com.alurafood.pedidos.service.PedidoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public Page<PedidoDTO> listar(@PageableDefault(size = 10)Pageable paginacao) {
        return pedidoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> listarPorId(@PathVariable("id") @NotNull Long id) {
        PedidoDTO dto = pedidoService.obterPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> realizarPedido(@RequestBody @Valid PedidoDTO dto, UriComponentsBuilder uriBuilder) {
        PedidoDTO pedido = pedidoService.criandoPedido(dto);
        URI endereco = uriBuilder.path("/pedido/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(endereco).body(pedido);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoDTO> atualizarStatus(@PathVariable("id") Long id, @RequestBody StatusDTO status) {
        PedidoDTO pedido = pedidoService.atualizarStatus(id, status);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}/pago")
    public ResponseEntity<Void> aprovarPagamento(@PathVariable @NotNull Long id) {
        pedidoService.aprovaPagamento(id);
        return ResponseEntity.ok().build();
    }
}


