package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.service.PagamentoService;
import lombok.AllArgsConstructor;
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
@RequestMapping("/pagamentos")
@AllArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @GetMapping
    public Page<PagamentoDTO> listar(@PageableDefault(size = 10)Pageable pageable) {
        return pagamentoService.obterTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> detalhar(@PathVariable("id") @NotNull Long id) {
        PagamentoDTO dto = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> cadastrar(@RequestBody @Valid PagamentoDTO dto, UriComponentsBuilder uriBuilder) {
        PagamentoDTO pagamento = pagamentoService.criaPagamento(dto);
        URI endereco = uriBuilder.path("/pagamento/{id}").buildAndExpand(pagamento.getId()).toUri();
        return ResponseEntity.created(endereco).body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable("id") @NotNull Long id, @RequestBody @Valid PagamentoDTO dto) {
        PagamentoDTO atualizado = pagamentoService.atualizarPagamento(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDTO> remover(@PathVariable("id") @NotNull Long id) {
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
