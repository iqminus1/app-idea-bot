package uz.pdp.appideabot.service;

import uz.pdp.appideabot.enums.Lang;
import uz.pdp.appideabot.enums.LangFields;

public interface LangService {
    String getMessage(LangFields keyword, Long userId);

    String getMessage(LangFields keyword, String lang);

    Lang getLanguageEnum(String text);
}
