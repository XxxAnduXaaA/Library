package com.example.Library.repository;

import com.example.Library.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note,Long> {
    public List<Note> getNoteByUserIdAndTextBookId(Long userId, Long TextBookId);
    public List<Note> getNotesByUserId(Long userId);
}
