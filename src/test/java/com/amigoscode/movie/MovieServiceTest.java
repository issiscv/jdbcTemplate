package com.amigoscode.movie;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieDao movieDao;

    @InjectMocks
    private MovieService movieService;

    private Movie mockMovie;

    private List<Movie> mockMovies;

    @BeforeEach
    void setUp() {
        mockMovie = new Movie(1, "Spider Man", List.of(), LocalDate.of(2021, 5, 20));
        mockMovies = Arrays.asList(
                mockMovie,
                new Movie(1, "Hulk", List.of(), LocalDate.of(2021, 5, 20)),
                new Movie(1, "Iron Man", List.of(), LocalDate.of(2021, 5, 20)));
    }

    @Test
    void getMovies() {

        when(movieDao.selectMovies()).thenReturn(mockMovies);

        List<Movie> movies = movieService.getMovies();

        assertThat(movies).hasSize(3)
                .extracting(Movie::name)
                .contains("Spider Man", "Hulk", "Iron Man");
    }

    @Test
    void getMovie() {

        when(movieDao.selectMovieById(anyInt())).thenReturn(Optional.of(mockMovie));

        Movie movie = movieService.getMovie(1);

        assertThat(movie).isEqualTo(mockMovie);
        assertThat(movie.name()).isEqualTo("Spider Man");
    }

    @Test
    void addNewMovie() {
        when(movieDao.insertMovie(any())).thenReturn(1);

        movieService.addNewMovie(mockMovie);

        verify(movieDao).insertMovie(any());
    }

    @Test
    void deleteMovie() {
        when(movieDao.selectMovieById(anyInt())).thenReturn(Optional.of(mockMovie));
        when(movieDao.deleteMovie(anyInt())).thenReturn(1);

        movieService.deleteMovie(1);

        verify(movieDao).deleteMovie(anyInt());
    }
}