package com.example.Library.repository;

import com.example.Library.entity.Bookmark;
import com.example.Library.entity.TextBook;
import com.example.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    public List<Bookmark> getBookmarksByUserIdAndTextBookId(Long userId, Long textBookId);
    public List<Bookmark> getAllByUserId(Long userId);
}
