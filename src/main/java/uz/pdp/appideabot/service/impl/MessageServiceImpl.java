package uz.pdp.appideabot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import uz.pdp.appideabot.enums.Lang;
import uz.pdp.appideabot.enums.LangFields;
import uz.pdp.appideabot.enums.State;
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
                    return;
                }
                switch (user.getState()) {
                    case START -> {
                        if (text.equals(langService.getMessage(LangFields.JOIN_WITH_CODE_TEXT, userId))) {
                            joinViaCode(userId);
                        } else if (text.equals(langService.getMessage(LangFields.MY_PROJECTS_TEXT, userId))) {
                            myProjects(userId);
                        } else if (text.equals(langService.getMessage(LangFields.BUTTON_LANG_SETTINGS, userId))) {
                            sendLanguages(userId);
                        }
                    }
                    case SELECTING_LANGUAGE -> {
                        setLanguage(userId, text);
                    }
                }
            }
        }
    }

    private void setLanguage(Long userId, String text) {
        Lang lang = langService.getLanguageEnum(text);
        User user = utils.getUser(userId);
        user.setState(State.START);
        String message = langService.getMessage(LangFields.EXCEPTION_LANGUAGE, userId);
        if (lang != null) {
            user.setLang(lang);
            message = langService.getMessage(LangFields.SUCCESSFULLY_SELECTED_LANGUAGE, userId);
        }
        sender.sendMessage(userId, message, buttonService.start(userId));
    }

    private void sendLanguages(Long userId) {
        User user = utils.getUser(userId);
        user.setState(State.SELECTING_LANGUAGE);
        sender.sendMessage(userId, langService.getMessage(LangFields.SELECT_LANGUAGE_TEXT, userId), buttonService.language(userId));
    }

    private void myProjects(Long userId) {

    }

    private void joinViaCode(Long userId) {

    }

    private void selectLanguage(Long userId) {
        String text = langService.getMessage(LangFields.CHOICE_LANGUAGE_TEXT, "uz");
        String uz = langService.getMessage(LangFields.BUTTON_LANGUAGE_UZ, "UZ");
        String ru = langService.getMessage(LangFields.BUTTON_LANGUAGE_RU, "RU");
        String en = langService.getMessage(LangFields.BUTTON_LANGUAGE_EN, "EN");
        LinkedHashMap<String, String> textData = new LinkedHashMap<>();
        textData.put(uz, AppConstants.LANG_DATA + "UZ");
        textData.put(ru, AppConstants.LANG_DATA + "RU");
        textData.put(en, AppConstants.LANG_DATA + "EN");
        InlineKeyboardMarkup markup = buttonService.callbackKeyboard(textData);
        sender.sendMessage(userId, text, markup);
    }

    private void start(Long userId) {
        User user = utils.getUser(userId);
        user.setState(State.START);
        sender.sendMessage(userId, langService.getMessage(LangFields.START_TEXT, userId), buttonService.start(userId));
    }
}
