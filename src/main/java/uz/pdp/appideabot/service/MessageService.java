package uz.pdp.appideabot.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageService {
    void process(Message message);
}
