package com.hackathon.jcs.modules.npc.model;

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
    private String personality;
    private String mood;

    @Column(length = 1000)
    private String initPrompt;

    @Builder
    public Npc(String name, String personality, String mood, String initPrompt) {
        this.name = name;
        this.personality = personality;
        this.mood = mood;
        this.initPrompt = initPrompt;
    }
}
