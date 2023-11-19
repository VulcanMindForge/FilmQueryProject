package com.skilldistillery.filmqueryproject.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.skilldistillery.filmqueryproject.entities.Actor;
import com.skilldistillery.filmqueryproject.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String dBUserName = "student";
	private static final String dBPassword = "student";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String sqlTxt = "SELECT * FROM film WHERE id = ?";

		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			PreparedStatement pstmt = conn.prepareStatement(sqlTxt);
			pstmt.setInt(1, filmId);
			ResultSet filmResult = pstmt.executeQuery();

			if (filmResult.next()) {
				film = new Film();
				film.setFilmId(filmId);
				film.setTitle(filmResult.getString("title"));
				film.setDesc(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getShort("release_year"));
				film.setLangId(filmResult.getInt("language_id"));
				film.setRentDur(filmResult.getInt("rental_duration"));
				film.setRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setRepCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setFeatures(stringToSet(filmResult.getString("special_features")));
				film.setActors(findActorsByFilmId(filmId));
			}

			filmResult.close();
			pstmt.close();
			conn.close();
			return film;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return film;
		}
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String sqlTxt = "SELECT * FROM actor WHERE id = ?";

		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			PreparedStatement pstmt = conn.prepareStatement(sqlTxt);
			pstmt.setInt(1, actorId);
			ResultSet actorResult = pstmt.executeQuery();

			if (actorResult.next()) {
				actor = new Actor();
				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));
				actor.setFilms(findFilmsByActorId(actorId));
			}
			conn.close();
			pstmt.close();
			actorResult.close();
			return actor;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return actor;
		}
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			String sql = "SELECT actor.* FROM actor JOIN film_actor ON actor.id = film_actor.actor_id JOIN film ON film_actor.film_id = film.id WHERE film.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int actorId = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");

				Actor actor = new Actor(firstName, lastName);
				actor.setId(actorId);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	@Override
	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			String sql = "SELECT film.* FROM film JOIN film_actor ON film.id = film_actor.film_id JOIN actor ON film_actor.actor_id = actor.id WHERE actor.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int filmId = rs.getInt("id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				short releaseYear = rs.getShort("release_year");
				int langId = rs.getInt("language_id");
				int rentDur = rs.getInt("rental_duration");
				double rate = rs.getDouble("rental_rate");
				int length = rs.getInt("length");
				double repCost = rs.getDouble("replacement_cost");
				String rating = rs.getString("rating");
				Set<String> features = stringToSet(rs.getString("special_features"));

				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features);
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public List<Film> findFilmByKeyWord(String keyWord) {
		List<Film> films = new ArrayList<>();
		keyWord = "%" + keyWord + "%"; 
		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			String sqlTxt = "SELECT * FROM film WHERE title LIKE ? OR description LIKE ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlTxt);
			pstmt.setString(1, keyWord);
			pstmt.setString(2, keyWord);
			ResultSet filmResult = pstmt.executeQuery();
			Film film = null;

			while (filmResult.next()) {
				film = new Film();
				film.setFilmId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDesc(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getShort("release_year"));
				film.setLangId(filmResult.getInt("language_id"));
				film.setRentDur(filmResult.getInt("rental_duration"));
				film.setRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setRepCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setFeatures(stringToSet(filmResult.getString("special_features")));
				film.setActors(findActorsByFilmId(filmResult.getInt("id")));
				films.add(film);
			}

			filmResult.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return films;
	}

	public Set<String> stringToSet(String stringToSplit) {
		Set<String> postSplitString = new HashSet<>();
		String[] splitString = stringToSplit.split(",");
		for (int i = 0; i < splitString.length; i++) {
			postSplitString.add(splitString[i]);
		}
		return postSplitString;
	}
	
	@Override
	public String findLanguageByID(int languageID) {
		String languageName = null;
		String sqlTxt = "SELECT * FROM language WHERE id = ?";

		try {
			Connection conn = DriverManager.getConnection(URL, dBUserName, dBPassword);
			PreparedStatement pstmt = conn.prepareStatement(sqlTxt);
			pstmt.setInt(1, languageID);
			ResultSet languageResult = pstmt.executeQuery();

			if (languageResult.next()) {
				languageName = languageResult.getString("name");
			}
			conn.close();
			pstmt.close();
			languageResult.close();
			return languageName;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return languageName;
		}
	}
}
