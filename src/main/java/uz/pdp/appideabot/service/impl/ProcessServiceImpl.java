package uz.pdp.appideabot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.appideabot.service.MessageService;
import uz.pdp.appideabot.service.ProcessService;

@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {
    private final MessageService messageService;

    @Override
    public void process(Update update) {
        if (update.hasMessage()) {
            messageService.process(update.getMessage());
        }
    }
}
