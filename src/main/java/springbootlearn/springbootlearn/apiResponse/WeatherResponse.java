package springbootlearn.springbootlearn.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {

   private Coord coord;
   private Weather[] weather;
   private Wind wind;
   private Main main;

   @Getter
   @Setter
   @AllArgsConstructor
   @NoArgsConstructor
   public static class Coord {

      @JsonProperty("lon")
      private double longitude;

      @JsonProperty("lat")
      private double latitude;
   }

   @Getter
   @Setter
   @AllArgsConstructor
   @NoArgsConstructor
   public static class Weather {

      @JsonProperty("description")
      private String description;

      private String icon;
   }

   @Getter
   @Setter
   @AllArgsConstructor
   @NoArgsConstructor
   public static class Wind {
      private double speed;
      private int deg;
   }

   @Getter
   @Setter
   @AllArgsConstructor
   @NoArgsConstructor
   public static class Main {
      private double temp;
      private int pressure;
      private int humidity;
      private double temp_min;
      private double temp_max;
   }
}
