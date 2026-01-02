package com.batuhn_ozdemir.order_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable; 

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent implements Serializable {
    private String orderNumber;
}
