package br.com.mesttra.order.amqp.message;

import br.com.mesttra.order.entity.BarItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BarRequestMessage {

    private List<BarItem> barItems;

    private String orderId;
}
