public class WarningWatcher extends WeatherObserver {

  private String countryWatched;
  private WeatherRecorder theRecorder;

  public WarningWatcher(WeatherRecorder aWeatherRecorder, String countryToWatch) {
    theRecorder = aWeatherRecorder;
    countryWatched = countryToWatch;
    theRecorder.attach(this);
  }

  public void update() {
  // Modify this so that it only prints out the update text if the update is a "Warning" for the country being watched
    String updateStatus = theRecorder.getUpdateType();
    if (updateStatus.equals("Warning") && this.countryWatched.equals(theRecorder.getUpdateCountry())){
    System.out.println("The WarningWatcher watching for Warnings for " +
                         countryWatched +
                         "\nhas noticed a new warning:\n\"" +
                         theRecorder.getUpdateText() + "\"\n");
  }
  }
}
