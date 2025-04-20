package com.jpdictionary.demo.repository;
import java.util.List;


import com.jpdictionary.demo.models.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    Optional<Word> findByWord(String word);
    
    List<Word> findByWordContainingIgnoreCaseOrReadingContainingIgnoreCaseOrMeaningContainingIgnoreCase(String word, String reading, String meaning);

}
