import java.util.Arrays;
import java.util.HashMap;

public class Main {
   private static final String gameLocations = """
           road,at the end of the road, W:hill, E:well house,S:valley,N:forest/n
           hill,on top of hill with a view in all directions,N:forest, E:road/n
           well house,inside a well house for a small spring,W:road,N:lake,S:stream/n
           valley,in a forest valley beside a tumbling stream,N:road,W:hill,E:stream/n
           forest,at the edge of a thick dark forest,S:road,E:lake/n
           lake,by an alpine lake surrounded by wildflowers,W:forest,S:well house/n
           stream,near a stream with a rocky bed,W:valley, N:well house/n
           """;

   public static void main(String[] args) {

      // got the main map working
      // NS: function to print the places description and the options to move, if Q then quit
      // function to move the player there, if Q then quit
      String[] locations = gameLocations.split("/n");
      String[] location = locations[1].split(",");
      System.out.println(location[3]);
      AdventureGame adventureGame = new AdventureGame();
      adventureGame.printGame("road");
   }
}