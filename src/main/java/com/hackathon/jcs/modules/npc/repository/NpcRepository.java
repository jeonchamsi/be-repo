package com.hackathon.jcs.modules.npc.repository;

import com.hackathon.jcs.modules.npc.model.Npc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NpcRepository extends JpaRepository<Npc, Long> {
}
