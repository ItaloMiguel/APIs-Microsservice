package br.com.alurafood.pedidos.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dataHora;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "pedido")
    private List<ItemDoPedido> itens = new ArrayList<>();
}
