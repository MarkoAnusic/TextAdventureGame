import javax.management.BadAttributeValueExpException;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

   public static void main(String[] args) {

      // got the main map print working
      // function to print the places description and the options to move, if Q then quit
      // move player function works by returning the next location player is going into or
      //						  null if the compassSign does not correlate to a nextLocation

      //NS: basic move while loop for playing the game

     /*
     for testing purposes
     player = adventureMap.getLocation("hill");
     adventureMap.printGame(player.getShortDesc());
     player = adventureMap.movePlayer(player.getShortDesc(), "S");
     System.out.println("\n" + player);
     */

      AdventureGame adventureMap = new AdventureGame();
      Location player = new Location(adventureMap, "road");
      System.out.println(player);
      adventureMap.chooseNextLocation(player);
      System.out.println(player);
      // ChooseNext Location returns null always, even after choosing right compass sign

   }
}