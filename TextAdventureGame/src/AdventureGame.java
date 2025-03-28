import java.util.*;
import java.util.concurrent.TimeUnit;

public class AdventureGame {

   public static final HashMap<String, Location> gameMap = new HashMap<>(getMap());

   public AdventureGame() {
   }

   private static final String gameLocations = """
           road,at the end of the road,W:hill,E:well house,S:valley,N:forest/n
           hill,on top of hill with a view in all directions,N:forest,E:road/n
           well house,inside a well house for a small spring,W:road,N:lake,S:stream/n
           valley,in a forest valley beside a tumbling stream,N:road,W:hill,E:stream/n
           forest,at the edge of a thick dark forest,S:road,E:lake/n
           lake,by an alpine lake surrounded by wildflowers,W:forest,S:well house/n
           stream,near a stream with a rocky bed,W:valley,N:well house
           """;
   //class for basic function of the game, printing text, the map, loading locations, adding new locations, etc.

   public Location getLocation(String shortDesc) {
      return gameMap.get(shortDesc);
   }

   private static HashMap<String, Location> getMap() {
      // function to get the base map for the game
      HashMap<String, Location> gameMap = new HashMap<>(); // base map that needs to be returned
      HashMap<String, String> directions = new HashMap<>(); // HashMap used for creating directions map for Location class
      String[] locations = gameLocations.split("/n");

      // for exp.    road,at the end of the road, W: hill, E:well house,S:valley,N:forest
      for(String locationData : locations) {
         String[] location = locationData.trim().split(","); // list of each comma delimited line, 0: shortDesc, 1: longDesc, 2 - 5: directions
         String shortDesc = location[0];
         String longDesc = location[1];
         // amount of the directions can be varied between 1 and 4
         for(int i = 2; i < location.length; i++) {
            String compassSign = String.valueOf(location[i].charAt(0));
            String shortDescNext = location[i].substring(location[i].indexOf(":") + 1);
            directions.put(compassSign, shortDescNext);
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
      System.out.printf("You are now *%s*\nIf you type these compass signs you will go to:\n", boardLocation.getLongDesc());
      boardLocation.printDirections();
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
      Location nextLocation = new Location();
      if(currentLocation != null) {
         if(currentLocation.getDirections().get(compassSign) != null) {
            // returns null?
            nextLocation = gameMap.get(currentLocation.getDirections().get(compassSign));
            return nextLocation;
         }
      }
      return null;
   }

   public Location chooseNextLocation(Location player) {

      //function for choosing the nextLocation for the player
      Scanner scanner = new Scanner(System.in);
      String chosenCompassSign;
      boolean quit = false;
      //choosing the location will run until this boolean is changed to true
      while(! quit) {
         printGame(player.getShortDesc());
         System.out.println("What location do you choose to go to? -->");
         chosenCompassSign = String.valueOf(scanner.next().charAt(0));
         if("NWSEnwse".contains(chosenCompassSign)) {
            // if the given compass Sign is NWSE it returns true, false if it is not
            player = movePlayer(player.getShortDesc(), chosenCompassSign);
            //returns null
            System.out.println(player);
            quit = true;
         }
         else if(chosenCompassSign.equalsIgnoreCase("Q")) {
            return null;
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

}
