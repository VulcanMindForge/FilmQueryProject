package com.skilldistillery.filmqueryproject.app;

import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmqueryproject.database.DatabaseAccessor;
import com.skilldistillery.filmqueryproject.database.DatabaseAccessorObject;
import com.skilldistillery.filmqueryproject.entities.Film;

public class FilmQueryApp {
	private boolean isQuit = false;
	private DatabaseAccessor db = new DatabaseAccessorObject();
	UI ui = new UI();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch(app);
	}

	private void launch(FilmQueryApp app) {
		displayWelcome();
		while (!isQuit) {
			mainMenu();
		}
		displayGoodbye();
	}

	private void mainMenu() {
		String choice = mainMenuPrompt();
		if (choice != null) {

			switch (choice) {
			case "Search by film ID.":
				searchID();
				break;
			case "Search by keyword.":
				searchKeyWord();
				break;
			case "Quit":
				quit();
				break;
			}
		}
	}

	private String mainMenuPrompt() {
		System.out.println();
		String prompt = "Which method you would like to use for your search.";
		List<String> menuOptions = new ArrayList<>();
		menuOptions.add("Search by film ID.");
		menuOptions.add("Search by keyword.");
		menuOptions.add("Quit");
		String choice = ui.userSelectFrom(prompt, menuOptions);
		return choice;
	}

	private void searchID() {
		int filmID = searchIDPrompt();
		Film film = db.findFilmById(filmID);
		if (film != null) {
			System.out.println(displayFilmPartial(db.findFilmById(filmID)));
			if (displayFullPrompt()) {
				System.out.println(displayFilmFull(db.findFilmById(filmID)));
				ui.holdForInput();
			}
			System.out.println("Returning to Main Menu.");
			mainMenu();
		} else {
			System.out.println("I am sorry, no film found by that ID. Returning to the Main Menu.\n");
			mainMenu();
		}
	}

	private int searchIDPrompt() {
		return ui.getUserInt("Please enter the film ID.");
	}

	private boolean displayFullPrompt() {
		System.out.println("Display the full film details?");
		return ui.userYesNo();
	}

	private void searchKeyWord() {
		String keyWord = searchKeyWordPrompt();
		List<Film> films = db.findFilmByKeyWord(keyWord);
		if (films.size() != 0) {

			System.out.println(films.size() + " Films found.\n");
			for (Film film : films) {
				System.out.println(film.getTitle() + ", ");
			}
			System.out.println("Displaying film details one at a time.");
			for (Film film : films) {
				System.out.println(displayFilmPartial(film));
				if (displayFullPrompt()) {
					System.out.println(displayFilmFull(film));
				}
				if (returnToMainPrompt()) {
					break;
				} else {
					continue;
				}
			}
		} else {
			System.out.println("We didn't find any films that matched that search criteria.\nReturning to Main Menu");
			mainMenu();
		}
	}

	private String searchKeyWordPrompt() {
		return ui.getUserString("Please enter the keyword or phrase to use in our search.");
	}

	private boolean returnToMainPrompt() {
		System.out.println("Would you like to return to the main menu?");
		return ui.userYesNo();
	}

	public void quit() {
		isQuit = true;
		ui.tearDown();
	}

	// ---------------------- Display Methods --------------------------------//
	public void displayWelcome() {
		System.out.println(
				"Welcome to our film queary app!\n" + "Below you will have the option to select a search method.\n"
						+ "You can look up a film by ID, or by search keyword.\n"
						+ "The keyword search will check the film title and description only.");
	}

	public String displayFilmPartial(Film film) {
		StringBuilder builder = new StringBuilder();
		builder.append("\nFilm ");
		builder.append(film.getFilmId());
		builder.append(" found:\n");
		if (film.getTitle() != null) {
			builder.append("Title: ");
			builder.append(film.getTitle());
			builder.append("\n");
		}
		builder.append("Year: ");
		builder.append(film.getReleaseYear());
		builder.append("\n");
		if (film.getRating() != null) {
			builder.append("Rating: ");
			builder.append(film.getRating());
			builder.append("\n");
		}
		if (film.getDesc() != null) {
			builder.append("Descrition: ");
			builder.append(film.getDesc());
			builder.append("\n");
		}
		builder.append("Language: ");
		builder.append(db.findLanguageByID(film.getLangId()));
		builder.append("\nActors appearing in this film: \n");
		builder.append(film.getActorNames());
		return builder.toString();
	}

	public String displayFilmFull(Film film) {
		StringBuilder builder = new StringBuilder();
		builder.append(displayFilmPartial(film));
		builder.append("\nFilm Length: ");
		builder.append(film.getLength());
		if (film.getFeatures() != null) {
			builder.append("\nSpecial Features: ");
			builder.append(film.getFeatures());
			builder.append("\n");
		}
		builder.append("Rental Period: ");
		builder.append(film.getRentDur());
		builder.append("\nRental Rate: $");
		builder.append(film.getRate());
		builder.append("\nReplacement Cost: $");
		builder.append(film.getRepCost());
		return builder.toString();
	}

	public void displayGoodbye() {
		System.out.println("Thank you for using our Film Query Service.\nHave a great day!");
	}

}
