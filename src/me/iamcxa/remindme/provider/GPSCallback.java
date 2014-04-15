package me.iamcxa.remindme.provider;
 
import android.location.Location;
 
public interface GPSCallback
{
        public abstract void onGPSUpdate(Location location);
}