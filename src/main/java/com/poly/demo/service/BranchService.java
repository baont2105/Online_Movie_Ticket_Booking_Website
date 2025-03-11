package com.poly.demo.service;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Movie;
import com.poly.demo.repository.BranchRepository;
import com.poly.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }
    

    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    public Branch addMovie(Branch branch) {
        return branchRepository.save(branch);
    }

    /*
     * 
    public Movie updateMovie(Long id, Movie updatedMovie) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setName(updatedMovie.getName());
                    movie.setTags(updatedMovie.getTags());
                    movie.setDuration(updatedMovie.getDuration());
                    movie.setReleaseDate(updatedMovie.getReleaseDate());
                    movie.setEndDate(updatedMovie.getEndDate());
                    movie.setViewCount(updatedMovie.getViewCount());
                    movie.setCountry(updatedMovie.getCountry());
                    movie.setProducer(updatedMovie.getProducer());
                    movie.setDirector(updatedMovie.getDirector());
                    movie.setActors(updatedMovie.getActors());
                    movie.setDescription(updatedMovie.getDescription());
                    movie.setThumbnail(updatedMovie.getThumbnail());
                    movie.setTrailer(updatedMovie.getTrailer());
                    return movieRepository.save(movie);
                })
                .orElse(null);
    }
     */

    public void deleteMovie(Long id) {
    	branchRepository.deleteById(id);
    }
}
