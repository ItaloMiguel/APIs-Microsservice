package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Transient;


@Service
@AllArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    private final ModelMapper modelMapper;

    public Page<PagamentoDTO> obterTodos(Pageable paginacao) {
        return pagamentoRepository.findAll(paginacao).map(p -> modelMapper.map(p, PagamentoDTO.class));
    }

    public PagamentoDTO obterPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Transactional
    public PagamentoDTO criaPagamento(PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        pagamentoRepository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Transactional
    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = pagamentoRepository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Transactional
    public void excluirPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }

}
