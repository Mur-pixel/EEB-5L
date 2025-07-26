package com.example.monoproj.game_chip.service.response;

import com.example.monoproj.game_chip.entity.GameChip;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ListGameChipResponse {
    private final List<GameChip> gameChipList;
    private final int currentPage;
    private final int totalPages;
    private final long totalItems;
    private final Map<Long, byte[]> thumbnailMap;

    public static ListGameChipResponse from(List<GameChip> gameChipList, int currentPage, int totalPages, long totalItems, Map<Long, byte[]> thumbnailMap) {
        return new ListGameChipResponse(gameChipList, currentPage, totalPages, totalItems, thumbnailMap);
    }

    public List<Map<String, Object>> toMapList() {
        return gameChipList.stream()
                .map(gc -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", gc.getId());
                    map.put("title", gc.getTitle());
                    map.put("description", gc.getDescription());
                    map.put("price", gc.getPrice());

                    byte[] thumbBytes = thumbnailMap.getOrDefault(gc.getId(), new byte[0]);
                    String base64Thumb = java.util.Base64.getEncoder().encodeToString(thumbBytes);
                    map.put("thumbnailImageData", base64Thumb);

                    return map;
                })
                .collect(Collectors.toList());
    }
}

