package uz.pdp.appideabot.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.appideabot.service.CallbackService;
import uz.pdp.appideabot.service.MessageService;

@Service
@RequiredArgsConstructor
public class BotProcessImpl implements BotProcess {
    private final MessageService messageService;
    private final CallbackService callbackService;

    @Override
    public void process(Update update) {
        if (update.hasMessage()) {
            messageService.process(update.getMessage());
        }else if (update.hasCallbackQuery()){
            callbackService.process(update.getCallbackQuery());
        }
    }
}
