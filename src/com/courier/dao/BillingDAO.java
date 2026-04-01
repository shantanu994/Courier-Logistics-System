package com.courier.dao;

import com.courier.model.Bill;
import com.courier.util.DatabaseException;
import java.util.ArrayList;

public interface BillingDAO {
    void createBill(Bill bill)                       throws DatabaseException;
    ArrayList<Bill> getAllBills()                     throws DatabaseException;
    Bill getBillByShipmentId(int shipmentId)         throws DatabaseException;
    void updatePaymentStatus(int billId,
         String status, String method)               throws DatabaseException;
}