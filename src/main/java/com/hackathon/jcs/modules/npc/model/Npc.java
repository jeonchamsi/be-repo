package com.hackathon.jcs.modules.npc.model;

import com.hackathon.jcs.modules.chat.dto.NpcChatResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Npc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String valence;
    private int valence_strength;
    private String mood;

    @Builder
    public Npc(String name, String valence, int valence_strength, String mood) {
        this.name = name;
        this.valence = valence;
        this.valence_strength = valence_strength;
        this.mood = mood;
    }

    public void updateFromAiResponse(NpcChatResponse response) {
        this.mood = response.mood();
        this.valence = response.valence();
        this.valence_strength = response.valence_strength();
    }
}
