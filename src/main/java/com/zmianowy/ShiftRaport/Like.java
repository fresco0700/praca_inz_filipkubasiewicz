package com.zmianowy.ShiftRaport;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@Data
public class Like {
    private String username;
    private LocalDateTime likedAt;

    public Like(String username) {
        this.username = username;
        this.likedAt = LocalDateTime.now();
    }

}
