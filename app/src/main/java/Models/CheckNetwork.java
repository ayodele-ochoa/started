package Models;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

public class CheckNetwork
{
    // Network check
    public static boolean isConnected(Context getApplicationContext)
    {
        boolean status = false;

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null && cm.getActiveNetwork() != null && cm.getNetworkCapabilities(cm.getActiveNetwork()) != null) {
                // connected to the internet
                status = true;
            }
        } else {
            if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting()) {
                // connected to the internet
                status = true;
            }
        }

        Log.i("Network Status: ", String.valueOf(status));
        return status;
    }

}
