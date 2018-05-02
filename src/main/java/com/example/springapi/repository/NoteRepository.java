package com.example.springapi.repository;

import com.example.springapi.model.Note;
import com.example.springapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUser(User user);

    Optional<Note> findByIdAndUser(Long id, User user);
}
