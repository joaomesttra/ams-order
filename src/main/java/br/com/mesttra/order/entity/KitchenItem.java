package br.com.mesttra.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class KitchenItem {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Integer quantity;

    @NonNull
    private String note;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

}
