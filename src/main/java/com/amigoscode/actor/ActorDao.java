package com.amigoscode.actor;


import java.util.List;
import java.util.Optional;

public interface ActorDao {
    List<Actor> selectActor();
    int insertActor(Actor movie);
    int deleteActor(int id);
    Optional<Actor> selectActorById(int id);
    // TODO: Update

}
