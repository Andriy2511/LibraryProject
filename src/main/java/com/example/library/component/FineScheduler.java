package com.example.library.component;

import com.example.library.model.Fine;
import com.example.library.model.Order;
import com.example.library.service.IFineService;
import com.example.library.service.IOrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class FineScheduler {

    private final IFineService fineService;
    private final IOrderService orderService;

    @Autowired
    public FineScheduler(IFineService fineService, IOrderService orderService, EntityManager entityManager) {
        this.fineService = fineService;
        this.orderService = orderService;
    }

    @Scheduled(cron = "0 44 20 * * *")
    public void checkOverdueBooksAndConfirmFine() {
        List<Order> overdueOrders = orderService.getOverdueOrders();
        if (!overdueOrders.isEmpty()) {
            for (Order order : overdueOrders) {
                if(order.getFine() == null) {
                    fineService.saveFine(setFineForOrder(order));
                }
            }
        }
    }

    public Fine setFineForOrder(Order order){
        Fine fine = new Fine();
        fine.setOrder(order);
        fine.setFineCost(100.0);
        fine.setPaid(false);
        return fine;
    }
}
