package com.example.ListArk.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 테스트 데이터 로더
 */
@Component
@RequiredArgsConstructor
public class TestDataLoader {

    private final ObjectMapper objectMapper;

    private static final String TEST_DATA_PATH = "test-data/";

    public String loadArmoryJson(String fileName) {
        return loadJson(TEST_DATA_PATH + "armory/" + fileName + ".json");
    }

    public String loadProfileJson(String fileName) {
        return loadJson(TEST_DATA_PATH + "profile/" + fileName + ".json");
    }

    public String loadEquipmentJson(String fileName) {
        return loadJson(TEST_DATA_PATH + "equipment/" + fileName + ".json");
    }

    public String loadEngravingJson(String fileName) {
        return loadJson(TEST_DATA_PATH + "engraving/" + fileName + ".json");
    }

    public String loadGemJson(String fileName) {
        return loadJson(TEST_DATA_PATH + "gem/" + fileName + ".json");
    }
    public String loadCombatSkillJson(String fileName) {
        return loadJson(TEST_DATA_PATH + "combatskill/" + fileName + ".json");
    }

    public String loadCardJson(String fileName) {
        return loadJson(TEST_DATA_PATH + "card/" + fileName + ".json");
    }

    public String loadCollectibleJson(String fileName) {
        return loadJson(TEST_DATA_PATH + "collectible/" + fileName + ".json");
    }

    public String loadColosseumJson(String fileName) {
        return loadJson(TEST_DATA_PATH + "colosseum/" + fileName + ".json");
    }

    public String loadArkPassiveJson(String fileName) {
        return loadJson(TEST_DATA_PATH + "arkpassive/" + fileName + ".json");
    }

    public String loadArkGridJson(String fileName) {
        return loadJson(TEST_DATA_PATH + "arkgrid/" + fileName + ".json");
    }


    private String loadJson(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            return new String(resource.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data: " + path, e);
        }
    }

    public <T> T parseJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }
}
