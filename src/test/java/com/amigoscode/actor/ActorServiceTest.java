package com.amigoscode.actor;

import com.amigoscode.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {

    @Mock
    private ActorDao actorDao;

    @InjectMocks
    private ActorService actorService;

    private Actor mockActor;
    private List<Actor> mockActors;

    @BeforeEach
    void setUp() {
        mockActor = new Actor(1, "Tom Hanks", 1);

        mockActors = Arrays.asList(
                mockActor,
                new Actor(2, "Brad Pitt", 2)
        );
    }

    @Test
    void testGetActors() {
        when(actorDao.selectActor()).thenReturn(mockActors);

        List<Actor> actors = actorService.getActors();

        assertThat(actors).hasSize(2)
                .extracting(Actor::name)
                .contains("Tom Hanks", "Brad Pitt");
    }

    @Test
    void testGetActor() {
        when(actorDao.selectActorById(1)).thenReturn(Optional.of(mockActor));

        Actor actor = actorService.getActor(1);

        String name = actor.name();
        assertThat(name).isEqualTo("Tom Hanks");
    }

    @Test
    void testAddActor() {
        Actor actor = new Actor(null, "Robert De Niro", 3);
        when(actorDao.insertActor(actor)).thenReturn(1);

        actorService.addActor(actor);

        verify(actorDao).insertActor(actor);
    }

    @Test
    void testDeleteActor() {
        when(actorDao.selectActorById(1)).thenReturn(Optional.of(mockActor));
        when(actorDao.deleteActor(1)).thenReturn(1);

        actorService.deleteActor(1);

        verify(actorDao).deleteActor(1);
    }

    @Test
    void testDeleteActorNotFound() {
        when(actorDao.selectActorById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> actorService.deleteActor(1));
    }

    @Test
    void testDeleteActorFailed() {
        when(actorDao.selectActorById(1)).thenReturn(Optional.of(mockActor));
        when(actorDao.deleteActor(1)).thenReturn(0);

        assertThrows(IllegalStateException.class, () -> actorService.deleteActor(1));
    }
}