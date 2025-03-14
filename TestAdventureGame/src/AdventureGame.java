import java.util.*;

public class AdventureGame {

   public HashMap<String, Location> gameMap = new HashMap<>();

   public AdventureGame() {
      gameMap = new HashMap<>(getMap());
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

   public HashMap<String, Location> getMap() {
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
      // function to print all the places in the main game HashMap
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

      Location boardLocaion = gameMap.get(shortDesc);
      System.out.printf("You are now *%s*\nIf you type these compass signs you will go to:\n", boardLocaion.getLongDesc());
      boardLocaion.printDirections();
      System.out.print("\n \t\t\t\t Q to quit");
   }

}
