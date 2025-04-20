package com.jpdictionary.demo.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "kanji")
public class Kanji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String kanji;

    private String onyomi;
    private String kunyomi;
    private String meaning;
    private int strokes;
    private int jlptLevel;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    // Constructor không tham số
    public Kanji() {
    }

    // Constructor đầy đủ
    public Kanji(Long id, String kanji, String onyomi, String kunyomi, String meaning, int strokes, int jlptLevel, Date createdAt) {
        this.id = id;
        this.kanji = kanji;
        this.onyomi = onyomi;
        this.kunyomi = kunyomi;
        this.meaning = meaning;
        this.strokes = strokes;
        this.jlptLevel = jlptLevel;
        this.createdAt = createdAt;
    }

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getOnyomi() {
        return onyomi;
    }

    public void setOnyomi(String onyomi) {
        this.onyomi = onyomi;
    }

    public String getKunyomi() {
        return kunyomi;
    }

    public void setKunyomi(String kunyomi) {
        this.kunyomi = kunyomi;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public int getStrokes() {
        return strokes;
    }

    public void setStrokes(int strokes) {
        this.strokes = strokes;
    }

    public int getJlptLevel() {
        return jlptLevel;
    }

    public void setJlptLevel(int jlptLevel) {
        this.jlptLevel = jlptLevel;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
