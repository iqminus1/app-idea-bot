package uz.pdp.appideabot.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.appideabot.utils.AppConstants;

@Component
public class Bot extends TelegramLongPollingBot {
    private final BotProcess botProcess;

    public Bot(BotProcess processService) {
        super(new DefaultBotOptions(), AppConstants.BOT_TOKEN);
        this.botProcess = processService;
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        botProcess.process(update);
    }

    @Override
    public String getBotUsername() {
        return AppConstants.BOT_USERNAME;
    }
}
