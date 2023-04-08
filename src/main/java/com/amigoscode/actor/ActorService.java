package com.amigoscode.actor;

import com.amigoscode.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ActorService {

    private final ActorDao actorDao;

    public ActorService(ActorDao actorDao) {
        this.actorDao = actorDao;
    }

    public List<Actor> getActors() {
        return actorDao.selectActor();
    }

    public Actor getActor(int id) {
        return actorDao.selectActorById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Actor with id %s not found", id)));
    }

    @Transactional
    public void addActor(Actor actor) {
        int result = actorDao.insertActor(actor);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    @Transactional
    public void deleteActor(int id) {
        Optional<Actor> optionalActor = actorDao.selectActorById(id);

        optionalActor.ifPresentOrElse(actor -> {
            int result = actorDao.deleteActor(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete Actor");
            }
        }, () -> {
            throw new NotFoundException(String.format("Actor with id %s not found", id));
        });
    }

}
