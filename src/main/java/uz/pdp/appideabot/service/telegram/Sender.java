package uz.pdp.appideabot.service.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import uz.pdp.appideabot.utils.AppConstants;

@Component
public class Sender extends DefaultAbsSender {
    public Sender() {
        super(new DefaultBotOptions(), AppConstants.BOT_TOKEN);
    }
}
