import java.util.*;
import java.util.concurrent.TimeUnit;


public class AdventureGame {

   private static String gameLocations = """
           road,at the end of the road,W:hill,E:well house,S:valley,N:forest,NE:lake,SE:stream/n
           hill,on top of hill with a view in all directions,N:forest,E:road,SE:valley/n
           well house,inside a well house for a small spring,W:road,N:lake,S:stream,NW:forest,SW:valley/n
           valley,in a forest valley beside a tumbling stream,N:road,W:hill,E:stream,NE:well house/n
           forest,at the edge of a thick dark forest,S:road,E:lake,SW:hill,SE:well house/n
           lake,by an alpine lake surrounded by wildflowers,W:forest,S:well house,SW:road/n
           stream,near a stream with a rocky bed,W:valley,N:well house,NW:road
           """;

   public static HashMap<String, Location> gameMap = new HashMap<>(getMap());
   public static Scanner scanner = new Scanner(System.in);

   public static void main(String[] args) {

      /* chooseNextLocation function return the nextLcoation player is going to,
       null if the compassSign is wrong, if he puts Q it will also print Exiting application..... and return null
       Basic movement works, until you put the correct compassSign everything works, If you enter Q, it exits
       */
      //NS: - Make admin controls, add/remove locations.
      //    - Make new directions possible, combinations of NSWE
      boolean playing = true;
      AdventureGame adventureMap = new AdventureGame();
      Location player = new Location(adventureMap, "road");

      while(playing) {
         // while loop for moving
         player = adventureMap.chooseNextLocation(player);
         if(player == null) {
            // can't be null, assigned from the start, movePlayer returns null if the wrong compassSign has been put in
            playing = false;
         }
      }


   }


   //class for basic function of the game, printing text, the map, loading locations, adding new locations, etc.

   public Location getLocation(String shortDesc) {
      return gameMap.get(shortDesc);
   }

   private static HashMap<String, Location> getMap() {
      // function to get the base map for the game
      HashMap<String, Location> gameMap = new HashMap<>(); // base map that needs to be returned
      HashMap<Location.DIRECTIONS, String> directions = new HashMap<>(); // HashMap used for creating directions map for Location class
      String[] locations = gameLocations.split("/n");

      // for exp.    road,at the end of the road, W: hill, E:well house,S:valley,N:forest
      for(String locationData : locations) {
         String[] location = locationData.trim().split(","); // list of each comma delimited line, 0: shortDesc, 1: longDesc, 2 - 9: directions
         String shortDesc = location[0];
         String longDesc = location[1];
         // amount of the directions can be varied between 1 and 8
         for(int i = 2; i < location.length; i++) {
            String compassSign = "";

            //If it is a direction like NW SE NE etc. : will be at location 2 not 1 like N:lake for exp.
            if(location[i].charAt(1) != ':') {
               compassSign = location[i].substring(0, 2);
            }
            else {
               compassSign = String.valueOf(location[i].charAt(0));
            }
            Location.DIRECTIONS direction = Location.DIRECTIONS.valueOf(compassSign); // proper import needed
            String shortDescNext = location[i].substring(location[i].indexOf(":") + 1);
            directions.put(direction, shortDescNext);
         }
         gameMap.put(shortDesc, new Location(shortDesc, longDesc, directions));
         directions.clear();

      }
      return gameMap;
   }

   public void printMap() {
      // function to print all the places in the main HashMap
      gameMap.forEach((k, v) -> System.out.println("key = " + k + ", value = " + v));
   }

   public void printGame(String shortDesc) {
      /* function to print the main game text
         for exp.
         ---------------------------
         You are now in *longDescription*
         You can go to :
         - compassSign - shortDesc;
         - same thing
         -
         -
                        Put in Q to quit
       */

      Location boardLocation = gameMap.get(shortDesc);
      List<Location.DIRECTIONS> stringDirections = new ArrayList<>(boardLocation.getDirections().keySet());
      Collections.sort(stringDirections);
      System.out.printf("You are now *%s*\nIf you type these compass signs you will go to:\n", boardLocation.getLongDesc());
      for(int i = 0; i < stringDirections.size(); i++) {
         System.out.println(stringDirections.get(i) + " - " + boardLocation.getDirections().get(stringDirections.get(i)));
      }
      System.out.println(" \t\t\t\t Q to quit");
   }

   public Location movePlayer(String currentShortDesc, String compassSign) {
      //Function to return the next location player picked to be moved at.
      //	1. Get currentLocation
      //	2. Check if the currentLocation has the compassSign to move there
      //	3. If it has, return the nextLocation, if not return null

      Location currentLocation = gameMap.get(currentShortDesc);
      //returns null if the compassSign
      //does not exist in the currentLocation player is in
      Location nextLocation;
      String nextShortDesc = currentLocation.getDirections().get(Location.DIRECTIONS.valueOf(compassSign)); // returns shortDesc of the nextLocation
      if(nextShortDesc != null) {
         nextLocation = gameMap.get(nextShortDesc);
         return nextLocation;
      }
      return null;
   }

   public Location chooseNextLocation(Location player) {

      //function for choosing the nextLocation for the player
      String chosenCompassSign;
      boolean quit = false;
      //choosing the location will run until this boolean is changed to true
      while(! quit) {
         printGame(player.getShortDesc());
         System.out.print("What location do you choose to go to? --> ");
         chosenCompassSign = String.valueOf(scanner.next()).toUpperCase();
         if("NW,SE,NE,SW,N,W,S,E".contains(chosenCompassSign) && player.getDirections().containsKey(Location.DIRECTIONS.valueOf(chosenCompassSign))) {
            // if the given compass Sign is NWSE or nwse it returns true, false if it is not
//            System.out.println("Current player in the chooseNextLocation: \n" + player);
//            System.out.println("-------------------------");
            player = movePlayer(player.getShortDesc(), chosenCompassSign.toUpperCase());
//            System.out.println("After movePlayer function\n" + player);
//            System.out.println("-------------------------");
            quit = true;
         }
         else if(chosenCompassSign.equalsIgnoreCase("Q")) {
            System.out.println("Exiting application...");
            try {
               TimeUnit.SECONDS.sleep(2);
            } catch(InterruptedException e) {
               throw new RuntimeException(e);
            }
            return null;
         }
         else if(chosenCompassSign.equalsIgnoreCase("admin")) {
            adminChecker();
         }
         else {
            System.out.println("You put in a wrong direction, wait while we get you back to the original menu.");
            try {
               TimeUnit.SECONDS.sleep(2);
            } catch(InterruptedException e) {
               throw new RuntimeException(e);
            }
         }
      }
      return player;
   }

   public void adminChecker() {
      System.out.print("Enter password -> ");
      String password = scanner.next().toUpperCase();

      if(password.equalsIgnoreCase("admin")) {
         System.out.println("Entering ADMIN mode: ");
         try {
            TimeUnit.SECONDS.sleep(2);
         } catch(InterruptedException e) {
            throw new RuntimeException(e);
         }
         adminCommands();
      }

   }

   public void adminCommands() {
      //function to add/remove location or change existing ones.
      System.out.println("Current list of locations:");
      //1. - road
      //2. - lake etc
      String[] locations = gameLocations.split("/n");
      String[] locationCommaSeparated;
      int j = 1;
      for(String information : locations) {
         locationCommaSeparated = information.trim().split(",");
         System.out.println(j + ". - " + locationCommaSeparated[0]);
         j++;
      }
      System.out.println("What do you want to do:");
      System.out.println("-----------------------------");
      System.out.println("CHANGE -> Change existing location.");
      System.out.println("ADD -> Add a location.");
      System.out.println("REMOVE -> REMOVE an existing location.");
      String choice = scanner.next().toUpperCase();
      switch(choice) {
         case "ADD": {
            System.out.print("Short Description ->");
            String shortDesc = scanner.next();
            System.out.print("Long Description ->");
            String longDesc = scanner.next();
            // make logic for possible directions, can't be two impossible connecting places
            //cabin, small cabin besides the woods,E:forest,S:hill,SE:road for exp.
            System.out.println("Define cardinal directions by adding short description of the location that is in that direction (null if you want that to be empty, we will do the rest.");
            HashMap<Location.DIRECTIONS, String> newDirections = new HashMap<>();
            List<String> cardinal = new ArrayList<>(List.of("North (N)", "East (E)", "South (S)", "West (W)"));
            String pointedShortDesc;
            boolean selecting = true;
            for(int i = 1; i <= 4; i++) {
               selecting = true;
               while(selecting) {
                  System.out.println(cardinal.get(i - 1) + " -> ");
                  pointedShortDesc = scanner.next();
                  if(gameMap.get(pointedShortDesc) != null) {
                     if(checkLocation(cardinal.get(i - 1), pointedShortDesc)) {
                        selecting = false;
                     }
                     else {
                        System.out.println("That shortDesc couldn't point to new location, try again or enter null if you don't want any.");
                        try {
                           TimeUnit.SECONDS.sleep(1);
                        } catch(InterruptedException e) {
                           throw new RuntimeException(e);
                        }
                     }
                  }
                  else {
                     System.out.println("No ShortDesc in map like that, bringing you back.");
                     try {
                        TimeUnit.SECONDS.sleep(1);
                     } catch(InterruptedException e) {
                        throw new RuntimeException(e);
                     }
                  }
               }
            }
         }
         case "REMOVE": {
            String updatedGameLocations = "";
            String shortDesc = "";
            int start = - 1;
            while(start == - 1) {
               System.out.print("Short Description of the location you want to remove -> ");
               shortDesc = scanner.next();
               start = gameLocations.indexOf(shortDesc);
            }
            System.out.println("You picked: " + gameLocations.substring(start, gameLocations.indexOf(",", start)));
            System.out.println("Removing it...");
            try {
               TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException e) {
               throw new RuntimeException(e);
            }
            String[] locationsInRows = gameLocations.trim().split("\n");
            for(String locationRow : locationsInRows) {
               if(locationRow.contains(shortDesc)) {
                  //skips the row where the shortDesc appears, thus removing it from the final updadetGameLocations
                  continue;
               }
               updatedGameLocations += locationRow;
            }

            gameLocations = updatedGameLocations;

         }
      }
   }

   public boolean checkLocation(String pointedDirection, String pointedShortDesc) {

      HashMap<String, String> opossiteCompassSignsMap = new HashMap<>();
      Location pointedLocation = gameMap.get(pointedShortDesc);
      opossiteCompassSignsMap.putAll(Map.of("N", "S", "E", "W", "S", "N", "W", "E", "NE", "SW", "SE", "NW", "SW", "NE", "NW", "SE"));
      //HashMap of opossite sites because if you enter N it return opossite, S, if you put S it returns N etc.
      Location.DIRECTIONS opossiteSign = Location.DIRECTIONS.valueOf(opossiteCompassSignsMap.get(pointedDirection));
      if(! pointedLocation.getDirections().containsKey(opossiteSign)) {
         return true;
      }
      //logic for when newLocation and the pointedLocation has only one connecting location or none, check for that


      return false;
   }

   public void testFunction() {
      // test function just to have it at hand if something doesn't work unexpectedly
      System.out.println("-------------------------------");
      System.out.println("TESTING:\n---------------------------\n");
      AdventureGame adventureMap = new AdventureGame();
      Location player = new Location(adventureMap, "road");
      System.out.println("Before chooseNextLocation \n" + player);
      System.out.println("---------------------------");
      player = adventureMap.chooseNextLocation(player);
      System.out.println("After choosing nextLocation: " + player);
      System.out.println("---------------------------");
   }

}