package com.skilldistillery.filmqueryproject.entities;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Film {
	private int filmId;
	private String title;
	private String desc;
	private short releaseYear;
	private int langId;
	private int rentDur;
	private double rate;
	private int length;
	private double repCost;
	private String rating;
	private Set<String> features;
	private List<Actor> actors;

	public Film() {
	}

	public Film(int filmId, String title, String desc, short releaseYear, int langId, int rentDur, double rate,
			int length, double repCost, String rating, Set<String> features) {
		this.filmId = filmId;
		this.title = title;
		this.desc = desc;
		this.releaseYear = releaseYear;
		this.langId = langId;
		this.rentDur = rentDur;
		this.rate = rate;
		this.length = length;
		this.repCost = repCost;
		this.rating = rating;
		this.features = features;
	}

	public int getFilmId() {
		return filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public short getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(short releaseYear) {
		this.releaseYear = releaseYear;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public int getRentDur() {
		return rentDur;
	}

	public void setRentDur(int rentDur) {
		this.rentDur = rentDur;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getRepCost() {
		return repCost;
	}

	public void setRepCost(double repCost) {
		this.repCost = repCost;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Set<String> getFeatures() {
		return features;
	}

	public void setFeatures(Set<String> features) {
		this.features = features;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	
	public String getActorNames() {
		StringBuilder builder = new StringBuilder();
		for (Actor actor : actors) {
			builder.append(actor.getFirstName());
			builder.append(" ");
			builder.append(actor.getLastName());
			builder.append("\n");
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(actors, desc, features, filmId, langId, length, rate, rating, releaseYear, rentDur, repCost,
				title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return Objects.equals(actors, other.actors) && Objects.equals(desc, other.desc)
				&& Objects.equals(features, other.features) && filmId == other.filmId && langId == other.langId
				&& length == other.length && Double.doubleToLongBits(rate) == Double.doubleToLongBits(other.rate)
				&& Objects.equals(rating, other.rating) && releaseYear == other.releaseYear && rentDur == other.rentDur
				&& Double.doubleToLongBits(repCost) == Double.doubleToLongBits(other.repCost)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Film [filmId=");
		builder.append(filmId);
		builder.append(", ");
		if (title != null) {
			builder.append("title=");
			builder.append(title);
			builder.append(", ");
		}
		if (desc != null) {
			builder.append("desc=");
			builder.append(desc);
			builder.append(", ");
		}
		builder.append("releaseYear=");
		builder.append(releaseYear);
		builder.append(", langId=");
		builder.append(langId);
		builder.append(", rentDur=");
		builder.append(rentDur);
		builder.append(", rate=");
		builder.append(rate);
		builder.append(", length=");
		builder.append(length);
		builder.append(", repCost=");
		builder.append(repCost);
		builder.append(", ");
		if (rating != null) {
			builder.append("rating=");
			builder.append(rating);
			builder.append(", ");
		}
		if (features != null) {
			builder.append("features=");
			builder.append(features);
			builder.append(", ");
		}
		if (actors != null) {
			builder.append("actors=");
			builder.append(actors);
		}
		builder.append("]");
		return builder.toString();
	}

}
