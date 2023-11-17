package com.skilldistillery.filmqueryproject.app;

import java.util.Scanner;

import com.skilldistillery.filmqueryproject.database.DatabaseAccessor;
import com.skilldistillery.filmqueryproject.database.DatabaseAccessorObject;
import com.skilldistillery.filmqueryproject.entities.Actor;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) {
    FilmQueryApp app = new FilmQueryApp();
    app.test();
//    app.launch();
  }

  private void test() {
    Actor actor = db.findActorById(6);
    System.out.println(actor);
  }

  private void launch() {
    Scanner input = new Scanner(System.in);
    
    startUserInterface(input);
    
    input.close();
  }

  private void startUserInterface(Scanner input) {
    
  }

}
