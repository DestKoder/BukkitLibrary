package ru.dest.library.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.object.Pair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Class provides simple methods to make HTTP Request to websites
 * @since 2.4
 */
public class RequestUtils {

    private enum Method {POST, GET}

    public static @Nullable String makeRequest(@NotNull String url, @NotNull Map<String,String> fields,@NotNull Method m, @Nullable List<Pair<String,String>> properties){
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod(m.name());

            connection.setDoOutput(true);

            if(properties != null){
                for(Pair<String,String> prop : properties){
                    connection.setRequestProperty(prop.getFirstVal(), prop.getSecondVal());
                }
            }

            //Send request
            if(!fields.isEmpty()){
                DataOutputStream wr = new DataOutputStream (
                        connection.getOutputStream());
                wr.writeBytes(getParamsString(fields));
                wr.flush();
                wr.close();
            }

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static @Nullable String makeRequest(String url, Map<String,String> fields, Method m){
        return makeRequest(url, fields, m, null);
    }

    public static @Nullable JsonElement requestJson(@NotNull String url, @NotNull Map<String,String> fields, Method m){
        String result = makeRequest(url, fields, m, Utils.newList(new Pair<>("Content-Type", "application/json; charset=UTF-8")));
        if(result == null) return null;

        return new JsonParser().parse(result);
    }

    private static String getParamsString(@NotNull Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

}
