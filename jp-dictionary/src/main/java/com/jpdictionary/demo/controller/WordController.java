package com.jpdictionary.demo.controller;

import com.jpdictionary.demo.models.Word;
import com.jpdictionary.demo.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/words")
public class WordController {

    @Autowired
    private WordService wordService;

    @GetMapping
    public List<Word> getAllWords(@RequestParam(value = "query", required = false) String query) {
        if (query != null && !query.isEmpty()) {
            return wordService.searchWords(query);
        }
        return wordService.getAllWords();
    }

    @GetMapping("/{word}")
    public Optional<Word> getWord(@PathVariable String word) {
        return wordService.getWordByName(word);
    }
    
    @GetMapping("/search")
    public List<Word> searchWords(@RequestParam String query) {
        return wordService.searchWords(query);
    }

    @PostMapping("/fetch/{word}")
    public Word fetchWord(@PathVariable String word) {
        return wordService.fetchWordFromAPI(word);
    }
    
    @PostMapping
    public Word createWord(@RequestBody Word word) {
        return wordService.saveWord(word);
    }

}
