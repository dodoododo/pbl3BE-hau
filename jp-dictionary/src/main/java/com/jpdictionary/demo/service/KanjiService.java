package com.jpdictionary.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpdictionary.demo.models.Kanji;
import com.jpdictionary.demo.repository.KanjiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class KanjiService {

    @Autowired
    private KanjiRepository kanjiRepository;

    private final String ALL_KANJI_API = "https://kanjiapi.dev/v1/kanji/all";
    private final String SINGLE_KANJI_API = "https://kanjiapi.dev/v1/kanji/";

    public List<Kanji> getKanjiFromAPI() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        String[] allKanji = restTemplate.getForObject(ALL_KANJI_API, String[].class);

        List<Kanji> savedKanji = new ArrayList<>();

        if (allKanji != null) {
            for (String k : allKanji) {
                try {
                    if (kanjiRepository.existsByKanji(k)) {
                        System.out.println("Đã tồn tại: " + k);
                        continue;
                    }

                    String apiUrl = SINGLE_KANJI_API + k;
                    JsonNode response = restTemplate.getForObject(apiUrl, JsonNode.class);

                    if (response != null) {
                        Kanji kanji = new Kanji();
                        kanji.setKanji(k);

                        // Lấy onyomi
                        JsonNode onyomiNode = response.get("on_readings");
                        if (onyomiNode != null && onyomiNode.isArray()) {
                            List<String> onyomiList = new ArrayList<>();
                            for (JsonNode o : onyomiNode) {
                                onyomiList.add(o.asText());
                            }
                            kanji.setOnyomi(String.join(", ", onyomiList));
                        }

                        // Lấy kunyomi
                        JsonNode kunyomiNode = response.get("kun_readings");
                        if (kunyomiNode != null && kunyomiNode.isArray()) {
                            List<String> kunyomiList = new ArrayList<>();
                            for (JsonNode ku : kunyomiNode) {
                                kunyomiList.add(ku.asText());
                            }
                            kanji.setKunyomi(String.join(", ", kunyomiList));
                        }

                        // Lấy stroke count
                        JsonNode strokes = response.get("stroke_count");
                        kanji.setStrokes(strokes != null ? strokes.asInt() : 0);

                        // Lấy meanings (không dịch nữa)
                        JsonNode meanings = response.get("meanings");
                        if (meanings != null && meanings.isArray()) {
                            List<String> meaningList = new ArrayList<>();
                            for (JsonNode meaningNode : meanings) {
                                meaningList.add(meaningNode.asText());
                            }
                            String meaningText = String.join(", ", meaningList);
                            kanji.setMeaning(meaningText);
                        }

                        // Lấy JLPT level
                        JsonNode jlptNode = response.get("jlpt");
                        kanji.setJlptLevel(jlptNode != null ? jlptNode.asInt() : 0);

                        kanjiRepository.save(kanji);
                        savedKanji.add(kanji);
                    }

                } catch (Exception ex) {
                    System.err.println("Lỗi khi xử lý kanji: " + k + " → " + ex.getMessage());
                }
            }
        }
        return savedKanji;
    }
}
