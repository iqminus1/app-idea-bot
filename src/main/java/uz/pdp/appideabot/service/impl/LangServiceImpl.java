package uz.pdp.appideabot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import uz.pdp.appideabot.enums.Lang;
import uz.pdp.appideabot.enums.LangFields;
import uz.pdp.appideabot.service.LangService;
import uz.pdp.appideabot.utils.CommonUtils;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LangServiceImpl implements LangService {
    private final MessageSource messageSource;
    private final CommonUtils commonUtils;

    @Override
    public String getMessage(LangFields keyword, Long userId) {
        return getMessage(keyword, commonUtils.getLang(userId));
    }

    @Override
    public String getMessage(LangFields keyword, String lang) {
        try {
            return messageSource.getMessage(keyword.name(), null, new Locale(lang));
        } catch (Exception e) {
            return messageSource.getMessage(keyword.name(), null, new Locale(Lang.UZ.name()));
        }
    }

    @Override
    public Lang getLanguageEnum(String text) {
        if (text.equals(getMessage(LangFields.BUTTON_LANGUAGE_UZ, "uz"))) {
            return Lang.UZ;
        } else if (text.equals(getMessage(LangFields.BUTTON_LANGUAGE_RU, "uz"))) {
            return Lang.RU;
        } else if (text.equals(getMessage(LangFields.BUTTON_LANGUAGE_EN, "uz"))) {
            return Lang.EN;
        }
        return null;
    }
}
