package com.amigoscode.actor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<Actor> listActor() {
        return actorService.getActors();
    }

    @GetMapping("/{id}")
    public Actor getActorId(@PathVariable("id") Integer id) {
        return actorService.getActor(id);
    }

    @PostMapping
    public void addActor(@RequestBody Actor actor) {
        actorService.addActor(actor);
    }

    @DeleteMapping("{id}")
    public void deleteActor(@PathVariable("id") Integer id) {
        actorService.deleteActor(id);
    }

}
