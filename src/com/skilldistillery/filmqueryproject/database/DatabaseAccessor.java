package com.skilldistillery.filmqueryproject.database;

import java.util.List;

import com.skilldistillery.filmqueryproject.entities.Actor;
import com.skilldistillery.filmqueryproject.entities.Film;

public interface DatabaseAccessor {
  public Film findFilmById(int filmId);
  public Actor findActorById(int actorId);
  public List<Actor> findActorsByFilmId(int filmId);
  public List<Film> findFilmsByActorId(int actorId);
  public List<Film> findFilmByKeyWord(String keyWord);
  public String findLanguageByID(int languageID);
}
