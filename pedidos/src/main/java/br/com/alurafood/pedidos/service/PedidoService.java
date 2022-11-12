package br.com.alurafood.pedidos.service;

import br.com.alurafood.pedidos.dto.PedidoDTO;
import br.com.alurafood.pedidos.dto.StatusDTO;
import br.com.alurafood.pedidos.model.Pedido;
import br.com.alurafood.pedidos.model.Status;
import br.com.alurafood.pedidos.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ModelMapper modelMapper;

    public Page<PedidoDTO> obterTodos(Pageable paginacao) {
        return pedidoRepository.findAll(paginacao).map(p -> modelMapper.map(p, PedidoDTO.class));
    }

    public PedidoDTO obterPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @Transactional
    public PedidoDTO criandoPedido(PedidoDTO dto) {
        Pedido pedido = modelMapper.map(dto, Pedido.class);
        pedido.setStatus(Status.REALIZADO);
        pedidoRepository.save(pedido);
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @Transactional
    public PedidoDTO atualizarStatus(Long id, StatusDTO dto) {
        Pedido pedido = pedidoRepository.porIdComItens(id);
        if(pedido == null) {
            throw new EntityNotFoundException();
        }
        pedido.setStatus(dto.getStatus());
        pedidoRepository.atualizarStatus(dto.getStatus(), pedido);
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @Transactional
    public void aprovaPagamento(Long id) {
        Pedido pedido = pedidoRepository.porIdComItens(id);
        if(pedido == null) {
            throw new EntityNotFoundException();
        }
        pedido.setStatus(Status.PAGO);
        pedidoRepository.atualizarStatus(Status.PAGO, pedido);
    }
}
