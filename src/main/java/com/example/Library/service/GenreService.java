package com.example.Library.service;//package com.example.library.service;

import com.example.Library.entity.Genre;
import com.example.Library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public Genre createGenre(Genre genre) {

        if(genreRepository.findByName(genre.getName()) == null){
            genre.setCreatedAt(new Date());
            genre.setModifiedAt(new Date());
            return genreRepository.save(genre);
        }
        else{
            //TO DO: Заменить return на exception
            return null;
        }
    }

    public Genre getGenreById(Long id){
        return genreRepository.findById(id).orElse(null);
    }

    public List<Genre> getAllGenres(){
        return genreRepository.findAll();
    }

    public Genre updateGenre(Long id, Genre updatedGenre){

        Genre existingGenre = genreRepository.findById(id).orElse(null);

        if(existingGenre != null){
            existingGenre.setName(updatedGenre.getName());

            existingGenre.setModifiedAt(new Date());
            return genreRepository.save(existingGenre);
        }

        else {
            return null;
        }

    }


}
