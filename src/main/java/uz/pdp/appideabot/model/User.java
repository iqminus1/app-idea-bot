package uz.pdp.appideabot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import uz.pdp.appideabot.enums.Lang;
import uz.pdp.appideabot.enums.State;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name = "users")
@Builder
public class User {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private Lang lang;

    @Enumerated(EnumType.STRING)
    private State state;

    private boolean prime;

    private LocalDateTime expire;
}
