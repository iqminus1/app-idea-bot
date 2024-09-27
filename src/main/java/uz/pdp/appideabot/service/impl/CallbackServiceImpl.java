package uz.pdp.appideabot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import uz.pdp.appideabot.enums.Lang;
import uz.pdp.appideabot.enums.LangFields;
import uz.pdp.appideabot.enums.State;
import uz.pdp.appideabot.model.User;
import uz.pdp.appideabot.repository.UserRepository;
import uz.pdp.appideabot.service.ButtonService;
import uz.pdp.appideabot.service.CallbackService;
import uz.pdp.appideabot.service.LangService;
import uz.pdp.appideabot.telegram.Sender;
import uz.pdp.appideabot.utils.AppConstants;
import uz.pdp.appideabot.utils.CommonUtils;

@Service
@RequiredArgsConstructor
public class CallbackServiceImpl implements CallbackService {
    private final CommonUtils utils;
    private final CommonUtils commonUtils;
    private final UserRepository userRepository;
    private final ButtonService buttonService;
    private final Sender sender;
    private final LangService langService;

    @Override
    public void process(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        User user = utils.getUser(userId);
        String data = callbackQuery.getData();
        switch (user.getState()) {
            case START -> {
                if (data.startsWith(AppConstants.LANG_DATA)) {
                    setFirstLang(userId, data.split(":")[1], callbackQuery.getMessage().getMessageId());
                }
            }
        }
    }

    private void setFirstLang(Long userId, String data, int messageId) {
        Lang lang = Lang.valueOf(data);
        User user = commonUtils.getUser(userId);
        user.setLang(lang);
        user.setState(State.START);
        userRepository.save(user);
        sender.deleteMessage(userId, messageId);
        sender.sendMessage(userId, langService.getMessage(LangFields.SUCCESSFULLY_SELECTED_LANGUAGE, userId), buttonService.start(userId));
    }
}
