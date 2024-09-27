package uz.pdp.appideabot.telegram;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotProcess {
    void process(Update update);
}
