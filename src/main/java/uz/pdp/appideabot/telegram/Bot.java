package uz.pdp.appideabot.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.appideabot.service.ProcessService;
import uz.pdp.appideabot.utils.AppConstants;

@Component
public class Bot extends TelegramLongPollingBot {
    private final ProcessService processService;

    public Bot(ProcessService processService) {
        super(new DefaultBotOptions(), AppConstants.BOT_TOKEN);
        this.processService = processService;
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        processService.process(update);
    }

    @Override
    public String getBotUsername() {
        return AppConstants.BOT_USERNAME;
    }
}
