package com.example.library.service.implementation;

import com.example.library.model.Message;
import com.example.library.model.Reader;
import com.example.library.repository.MessageRepository;
import com.example.library.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements IMessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> findMessagesByReaderWithPagination(Reader reader, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return messageRepository.findMessageByReader(reader, pageable).getContent();
    }

    @Override
    public Long getMessagesCountByReader(Reader reader) {
        return messageRepository.countMessageByReader(reader);
    }

    @Override
    public Message findMessageById(Long messageId) {
        return messageRepository.findById(messageId).get();
    }

    @Override
    public void saveMessage(Message message){
        messageRepository.save(message);
    }
}
