package com.sha.springbootdeviceseller.repository.projection;

import com.sha.springbootdeviceseller.model.DeviceType;

import java.time.LocalDateTime;

public interface PurchaseItem {
    String getName();
    DeviceType getType();
    Double getPrice();
    String getColor();
    LocalDateTime getPurchaseTime();
}
