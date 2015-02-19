/**
 * NXT access classes.
 */
package lejos.nxt;

/**
 * Provides access to Battery.
 */
public class Battery
{

  private Battery()
  {
  }
  
  /**
   * Returns the battery voltage in millivolts.
   * 
   * @return Battery voltage in mV.
   */
  public static native int getVoltageMilliVolt();

  /**
   * Returns the battery voltage in volts.
   * 
   * @return Battery voltage in Volt.
   */
  public static float getVoltage()
  {
    return 0.0f; //(float)(Battery.getVoltageMilliVolt() * 0.001);
  }
}
