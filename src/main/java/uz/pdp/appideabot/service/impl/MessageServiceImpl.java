package uz.pdp.appideabot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import uz.pdp.appideabot.enums.LangFields;
import uz.pdp.appideabot.model.User;
import uz.pdp.appideabot.service.ButtonService;
import uz.pdp.appideabot.service.LangService;
import uz.pdp.appideabot.service.MessageService;
import uz.pdp.appideabot.telegram.Sender;
import uz.pdp.appideabot.utils.AppConstants;
import uz.pdp.appideabot.utils.CommonUtils;

import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final CommonUtils utils;
    private final LangService langService;
    private final ButtonService buttonService;
    private final Sender sender;

    @Override
    public void process(Message message) {
        if (message.getChat().getType().equals("private")) {
            if (message.hasText()) {
                String text = message.getText();
                Long userId = message.getFrom().getId();
                User user = utils.getUser(userId);
                if (user.getLang() == null) {
                    selectLanguage(userId);
                    return;
                }
                if (text.equals("/start")) {
                    start(userId);
                }
            }
        }
    }

    private void selectLanguage(Long userId) {
        String text = langService.getMessage(LangFields.CHOICE_LANGUAGE_TEXT, "uz");
        String uz = langService.getMessage(LangFields.BUTTON_LANGUAGE_UZ, "uz");
        String ru = langService.getMessage(LangFields.BUTTON_LANGUAGE_RU, "ru");
        String en = langService.getMessage(LangFields.BUTTON_LANGUAGE_EN, "en");
        LinkedHashMap<String, String> textData = new LinkedHashMap<>();
        textData.put(uz, AppConstants.LANG_DATA + "uz");
        textData.put(ru, AppConstants.LANG_DATA + "ru");
        textData.put(en, AppConstants.LANG_DATA + "en");
        InlineKeyboardMarkup markup = buttonService.callbackKeyboard(textData);
        sender.sendMessage(userId, text, markup);
    }

    private void start(Long userId) {

    }
}
