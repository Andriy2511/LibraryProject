package com.example.library.component;

import com.example.library.model.Message;
import com.example.library.model.Order;
import com.example.library.model.Reader;
import com.example.library.service.IMessageService;
import com.example.library.service.IOrderService;
import com.example.library.service.IReaderService;
import com.example.library.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@EnableScheduling
public class MessageScheduler {

    private final IReaderService readerService;
    private final IRoleService roleService;
    private final IOrderService orderService;
    private final IMessageService messageService;

    @Autowired
    public MessageScheduler (IReaderService readerService, IRoleService roleService, IOrderService orderService, IMessageService messageService){
        this.readerService = readerService;
        this.roleService = roleService;
        this.orderService = orderService;
        this.messageService = messageService;
    }

    /**
     * Checks current orders if they are not overdue. If some order is overdue,
     * the method sends a message to the user asking them to return the book.
     * Also, it sends a message to the admin that the user has an overdue order.
     */
    // 0 0 12 * * * - 12:00
    //crontab.guru
    @Scheduled(cron = "0 0 12 * * *")
    public void checkOverdueBooksAndSendMessages() {
        List<Order> overdueOrders = orderService.getOverdueOrders();
        Map<Reader, List<Order>> readerOrdersMap = formMapFromList(overdueOrders);
        readerOrdersMap.forEach(this::sendMessages);
    }

    private String getCurrentDate(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        return currentTime.format(dateTimeFormatter);
    }

    private String printMessageToAdmin(Reader reader, List<Order> overdueOrders){
        StringBuilder messageToAdmin = new StringBuilder();
        messageToAdmin.append("The user: ").append(reader.getUsername()).append(" has overdue the following orders:\n ");
        for(int i = 0; i < overdueOrders.size(); i++){
            messageToAdmin.append(i + 1).append(". ").append(overdueOrders.get(i).getBook().getTitle()).append(".\n");
            messageToAdmin.append("Order date: ").append(overdueOrders.get(i).getOrderDate()).append("\n");
            messageToAdmin.append("Return date: ").append(overdueOrders.get(i).getReturnDate()).append("\n");
        }

        messageToAdmin.append(getCurrentDate());

        return messageToAdmin.toString();
    }

    private String printMessageToReader(Reader reader, List<Order> overdueOrders){
        StringBuilder messageToReader = new StringBuilder();
        messageToReader.append("Hello, ").append(reader.getUsername()).append("!\n");
        messageToReader.append("We remind you that you must return the following books:\n");
        for(int i = 0; i < overdueOrders.size(); i++){
            messageToReader.append(i + 1).append(". ").append(overdueOrders.get(i).getBook().getTitle()).append(".\n");
            messageToReader.append("Order date: ").append(overdueOrders.get(i).getOrderDate()).append("\n");
            messageToReader.append("Return date: ").append(overdueOrders.get(i).getReturnDate()).append("\n");
        }
        messageToReader.append("If you don't return these books immediately you will be fined");
        messageToReader.append(getCurrentDate());

        return messageToReader.toString();
    }

    private Map<Reader , List<Order>> formMapFromList(List<Order> orderList){
        Map<Reader , List<Order>> readerOrdersMap = new HashMap<>();
        for (Order order : orderList) {
            Reader reader = order.getReader();
            if(!readerOrdersMap.containsKey(reader)){
                readerOrdersMap.put(reader, new ArrayList<>());
            }
            readerOrdersMap.get(reader).add(order);
        }

        return readerOrdersMap;
    }

    private void sendMessages(Reader reader, List<Order> overdueOrders){
        String messageToAdmin = printMessageToAdmin(reader, overdueOrders);
        String messageToReader = printMessageToReader(reader, overdueOrders);
        Message message = new Message();

        message.setReader(reader);
        message.setTitle("Overdue book");
        message.setMessage(messageToReader);
        messageService.saveMessage(message);

        List<Reader> adminList = readerService.findAllReadersByRoles(roleService.findRoleByName("ADMIN"));
        for(Reader admin : adminList){
            message = new Message();
            message.setTitle("An overdue book for the reader: " + reader.getUsername());
            message.setMessage(messageToAdmin);
            message.setReader(admin);
            messageService.saveMessage(message);
        }
    }
}
