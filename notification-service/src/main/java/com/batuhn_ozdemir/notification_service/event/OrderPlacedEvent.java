package com.batuhn_ozdemir.notification_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable; 

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent implements Serializable { // BU EKLENDİ
    private String orderNumber;
}
