package uz.pdp.appideabot.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.pdp.appideabot.enums.Lang;
import uz.pdp.appideabot.enums.State;
import uz.pdp.appideabot.model.User;
import uz.pdp.appideabot.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@RequiredArgsConstructor
public class CommonUtils {
    ConcurrentMap<Long, User> users = new ConcurrentHashMap<>();
    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return users.computeIfAbsent(userId, k ->
                userRepository.findById(userId).orElseGet(() ->
                        userRepository.save(User.builder()
                                .id(userId)
                                .expire(LocalDateTime.now().minusDays(3))
                                .prime(false)
                                .build())));
    }

    public State getState(Long userId) {
        return getUser(userId).getState();
    }

    public String getLang(Long userId) {
        Lang lang = getUser(userId).getLang();
        if (lang == null)
            return null;
        return lang.toString();
    }


    @Scheduled(cron = "0 0 4 * * ?")
    public void saveUsers() {
        List<User> list = users.values().stream().toList();
        userRepository.saveAll(list);
        users.clear();
    }
}
