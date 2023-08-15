package com.example.library.listener;

import com.example.library.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AuthenticationListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        addNewUnconfirmedOrderList();
    }

    /**
     * The method creates a new order list for the orders that the user will be able to make after authorization.
     */
    private void addNewUnconfirmedOrderList(){
        List<Order> unconfirmedOrders = new ArrayList<>();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        session.setAttribute("unconfirmedOrders", unconfirmedOrders);
        log.info("Order list is: {}", unconfirmedOrders);
    }
}
