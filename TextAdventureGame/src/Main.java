import javax.management.BadAttributeValueExpException;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

   public static void main(String[] args) {

      /* chooseNextLocation function return the nextLcoation player is going to,
       null if the compassSign is wrong, if he puts Q it will also print Exiting application..... and return null
       */
      //NS: Make a while loop for movement, there is a while loop  in chooseNextLocation for inputting a compassSign

      boolean playing = true;
      AdventureGame adventureMap = new AdventureGame();
      Location player = new Location(adventureMap, "road");

      while(playing) {
         // while loop for moving
         player = adventureMap.chooseNextLocation(player);
         if(player == null) {
            playing = false;
         }

      }
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