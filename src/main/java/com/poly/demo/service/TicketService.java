package com.poly.demo.service;

import com.poly.demo.entity.Movie;
import com.poly.demo.entity.Ticket;
import com.poly.demo.entity.User;
import com.poly.demo.repository.MovieRepository;
import com.poly.demo.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTicket() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketByUserID(Integer userId) {
        return ticketRepository.findTicketsByUserId(userId);
    }

    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    /*
     * 
     * public Movie updateMovie(Long id, Movie updatedMovie) {
        return ticketRepository.findById(id)
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
    	ticketRepository.deleteById(id);
    }
}
