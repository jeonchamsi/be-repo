package com.hackathon.jcs.modules.chat.model;

import com.hackathon.jcs.modules.npc.model.Npc;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "npc_id")
    private Npc npc;

    @Builder
    public Chat(String message, Npc npc) {
        this.message = message;
        this.npc = npc;
    }
}
