package com.sha.springbootdeviceseller.service;

import com.sha.springbootdeviceseller.model.Purchase;
import com.sha.springbootdeviceseller.repository.projection.PurchaseItem;

import java.util.List;

public interface PurchaseService {
    Purchase savePurchase(Purchase purchase);

    List<PurchaseItem> findPurchaseItemsOfUser(Long id);
}
