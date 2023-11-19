package com.skilldistillery.filmqueryproject.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class UI {
	private final String onInvalidMessage = "Invalid input.";
	private final Scanner sc = new Scanner(System.in);
	public UI() {
	}

	public Boolean userYesNo() {
		List<String> yesNo = new ArrayList<>();
		yesNo.add("Yes");
		yesNo.add("No");
		String answer = userSelectFrom(yesNo);
		return answer.equalsIgnoreCase("yes");
	}

	public String userSelectFrom(String message, List<String> options) {
		StringBuilder builder = new StringBuilder(message + "\nPlease select an option by number.\n");
		for (int i = 0; i < options.size(); i++) {
			builder.append(" " + (i + 1) + " -- " + options.get(i) + "\n");
		}
		
		Integer userSelection = checkUserInput(builder.toString(), Integer::parseInt,
				i -> (1 <= i && i <= options.size())) - 1;

		return (userSelection == null) ? null : options.get(userSelection);
	}

	public String userSelectFrom(List<String> options) {
		return userSelectFrom("", options);
	}

	public Integer getUserInt(String message) {
		return checkUserInput(message, Integer::parseInt, a -> true);
	}

	public Integer getUserInt() {
		return getUserInt("");
	}

	public String getUserString(String message) {
		System.out.println();
		return checkUserInput(message, str -> str, str -> true);
	}

	public String getUserString() {
		return getUserString("");
	}

	private <T> T checkUserInput(String message, Function<String, T> parseMethod, Function<T, Boolean> isValidChecker) {
		while (true) {
			try {
				System.out.println(message);	
				String input = sc.nextLine();

				T parsed = parseMethod.apply(input);

				if (isValidChecker.apply(parsed)) {
					System.out.println();
					return parsed;
				} else {
					throw new IllegalArgumentException();
				}
			} catch (IllegalArgumentException e) {
				System.err.println(onInvalidMessage);
			}
		}
	}
}
