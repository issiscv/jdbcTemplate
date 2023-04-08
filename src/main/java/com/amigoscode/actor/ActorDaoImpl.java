package com.amigoscode.actor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActorDaoImpl implements ActorDao {

    private final JdbcTemplate jdbcTemplate;

    public ActorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Actor> selectActor() {
        String sql = """
                SELECT id, name, movie 
                FROM actor
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, (resultSet, i) ->
                new Actor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("movie")
                ));
    }

    @Override
    public int insertActor(Actor actor) {
        String sql = """
                INSERT INTO actor(name, movie)
                VALUES (?, ?);
                """;
        return jdbcTemplate.update(sql, actor.name(), actor.movieId());
    }

    @Override
    public int deleteActor(int id) {
        String sql = """
                DELETE FROM actor
                WHERE id = ?;
                """;
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Actor> selectActorById(int id) {
        String sql = """
                SELECT id, name, movie 
                FROM actor
                WHERE id = ?
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, (resultSet, i) ->
                new Actor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("movie")
                ), id).stream().findFirst();
    }
}
