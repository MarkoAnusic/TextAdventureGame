import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Location {

   private String shortDesc;
   private String longDesc;
   private HashMap<String, String> directions; // HashMap<NWSE, String>, every direction points to another location by compass sign since the main HAsh map will have all location keyed by the short desc

   public Location(String shortDesc, String longDesc, HashMap<String, String> directions) {
      this.shortDesc = shortDesc;
      this.longDesc = longDesc;
      this.directions = new HashMap<>();
      this.directions.putAll(directions);
   }

   public Location(String shortDesc, String longDesc) {
      this.shortDesc = shortDesc;
      this.longDesc = longDesc;
      directions = new HashMap<>();
   }

   public HashMap<String, String> getDirections() {
      return directions;
   }

   public String getShortDesc() {
      return shortDesc;
   }

   public void setShortDesc(String shortDesc) {
      this.shortDesc = shortDesc;
   }

   public String getLongDesc() {
      return longDesc;
   }

   public void setLongDesc(String longDesc) {
      this.longDesc = longDesc;
   }

   public void addDirection(String direction, String location) {
      //function in the AdventureGame class that checks if the location exists and if the direction
      //length is equal to 1 so it can be added.
      directions.put(direction, location);


   }

   public void printDirections() {
      getDirections().forEach((k, v) -> System.out.println(k + " - " + v));
   }
}

