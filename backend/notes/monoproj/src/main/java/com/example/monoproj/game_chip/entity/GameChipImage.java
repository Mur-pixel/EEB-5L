package com.example.monoproj.game_chip.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class GameChipImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @Column(nullable = false)
    private String originalFileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameChipImageType type; // THUMBNAIL or DETAIL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_chip_id", nullable = false)
    private GameChip gameChip;

    public GameChipImage(byte[] imageData, String originalFileName, GameChipImageType type, GameChip gameChip) {
        this.imageData = imageData;
        this.originalFileName = originalFileName;
        this.type = type;
        this.gameChip = gameChip;
    }
}
