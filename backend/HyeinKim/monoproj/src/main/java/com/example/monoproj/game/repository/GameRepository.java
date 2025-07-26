package com.example.monoproj.game.repository;

import com.example.monoproj.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

// DB접근을 위한 인터페이스(CRUD 작업을 수행하는 메서드를 기본 제공), <엔티티 클래스, 엔티티의 ID 타입>
public interface GameRepository extends JpaRepository<Game, Long> {
}
