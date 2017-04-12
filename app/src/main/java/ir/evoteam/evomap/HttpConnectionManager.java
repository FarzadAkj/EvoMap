package ir.evoteam.evomap;


        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;

        import java.io.BufferedReader;
        import java.io.DataOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;


public class HttpConnectionManager {

    static  HttpURLConnection urlConnection = null;
    static final String serverUrl = "http://192.168.1.4:3000/api";


    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
            return true;
        else
            return false;
    }

    public static String inputStreamToString(InputStream inputstream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getDataHttpUrlConnection() {
        try {
            URL url = new URL(serverUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream is = urlConnection.getInputStream();
            String response = inputStreamToString(is);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            urlConnection.disconnect();
            return null;
        }
    }

    public static int postDataHttpUrlConnection(String json) {
        try {

            URL url = new URL(serverUrl);
            urlConnection = (HttpURLConnection) url.openConnection();

            String urlParameters = json;
//            String urlParameters = "[{\"Driver_ID\":16398,\"Longtitude\":\"16.253642\",\"Latitude\":\"16.253642\",\"Driver_State\":1,\"Date_time\":\"2017-12-7_16:38\"}, {\"Driver_ID\":16398,\"Longtitude\":\"16.253642\",\"Latitude\":\"16.253642\",\"Driver_State\":1,\"Date_time\":\"2017-12-7_16:38\"}, {\"Driver_ID\":16398,\"Longtitude\":\"16.253642\",\"Latitude\":\"16.253642\",\"Driver_State\":1,\"Date_time\":\"2017-12-7_16:38\"}]";
//            String urlParameters = "[{\"color\": \"red\",\"value\": \"#f00\"},{\"color\": \"green\",\"value\": \"#0f0\"}]";
//                                   "[{"Driver_ID":4,"Longtitude":"null","Latitude":"null","Driver_State":null,"Date_time":"null"}]"
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(urlConnection.getOutputStream());
            dStream.writeBytes(urlParameters);
            dStream.flush();
            dStream.close();

            return urlConnection.getResponseCode();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

    }
}

