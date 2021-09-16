package br.com.mesttra.order.amqp.message;

import br.com.mesttra.order.entity.KitchenItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class KitchenRequestMessage {

    List<KitchenItem> items;

    String orderId;

}
