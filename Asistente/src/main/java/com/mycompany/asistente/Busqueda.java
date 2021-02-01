/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.asistente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;

/**
 *
 * @author danko
 */
public class Busqueda {

    private static final String urlHead = "https://api.duckduckgo.com/?q=";
    private static final String urlTail = "&format=json&pretty=3";

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "https://www.google.com/search?q=javaguides";

    public static void verJson(String query) {
        String output = urlHead + query + urlTail;
        System.out.println(output);
    }

    //hacer el request http y devuelve un json en forma de string
    private static String sendHttpGETRequest(String query) throws IOException {
        try {
            String json;

            URL obj = new URL(urlHead + query + urlTail);
            HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                //System.out.println("ESTO SALE: " + response.toString());
                json = response.toString();

            } else {
                System.out.println("GET request not worked");
                json = "error";
            }
            return json;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error de malformed");
        }
        return null;//esto no deberia retornar

    }

    //hacer el request http y devuelve un json en forma de string
    private static String sendHttpGETRequest(String query, String head, String tail) throws IOException {
        try {
            String json;

            URL obj = new URL(head + query + tail);
            System.out.println(head + query + tail);
            System.out.println("query: " + query);
            HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                //System.out.println("ESTO SALE: " + response.toString());
                json = response.toString();

            } else {
                System.out.println("GET request not worked");
                json = "error";
            }
            return json;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error de malformed");
        }
        return null;//esto no deberia retornar

    }

    //convertir json a map
    private static Map jsonToMap(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // convert JSON string to Java Map
        Map<String, Object> map = new ObjectMapper().readValue(json, Map.class);
        //System.out.println("About: "+map.get("Abstract"));
        return map;

        // print map keys and values
        /*
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
         */
    }

    //para extraer campos de el mapa que json
    private static String getField(Map map) {//

        String abstracto = (String) map.get("Abstract");
        if (abstracto == null || abstracto.equals("")) {
            abstracto = "i did't find about it";
        }

        //System.out.println("esto se va a imprimir "+", "+abstracto);
        return abstracto;
    }

    //para extraer campos de el mapa que json
    private static String getField(Map map, String field) {//

        String abstracto = (String) map.get("Abstract");
        if (abstracto == null || abstracto.equals("")) {
            abstracto = "i did't find about it";
        }

        //System.out.println("esto se va a imprimir "+", "+abstracto);
        return abstracto;
    }

    public static String busqueda(String query) throws IOException {
        System.out.println("" + query);
        String json = sendHttpGETRequest(query);
        //System.out.println("JSON: "+json);
        Map map = jsonToMap(json);
        return getField(map, "AbstractText");
    }

    public static int cuantaPeli(String movie) throws IOException {
        String json = sendHttpGETRequest(movie, "https://imdb-api.com/en/API/SearchMovie/k_hwum2x9b/", "");
        System.out.println("json:" + json+movie);
        ObjectMapper mapper = new ObjectMapper();
        Map map = jsonToMap(json);
        ArrayList results = (ArrayList) map.get("results");
        // convert JSON string to Java List
        LinkedHashMap linekd = (LinkedHashMap) results.get(0);//las liked hash map son mapas con orden(como las listas enlazadas)
        linekd.get("title");
        System.out.println("" + linekd.get("title"));
        System.out.println("results: " + results.size());
        //ArrayList results =(ArrayList) new ObjectMapper().readValue(reul, new TypeReference<ArrayList>(){});

        //System.out.println(results.size());
        //System.out.println("About: "+map.get("Abstract"));
        return results.size();

        //return getField(map,"results").length();
    }

    public static void hello() {
        System.out.println("hello");
    }

    public static String cualespelis(String movie) throws IOException {
        String json = sendHttpGETRequest(movie, "https://imdb-api.com/en/API/SearchMovie/k_hwum2x9b/", "");
        System.out.println("json:" + json);
        ObjectMapper mapper = new ObjectMapper();
        Map map = jsonToMap(json);
        ArrayList results = (ArrayList) map.get("results");
        System.out.println("results: "+results.getClass()+results.toString());
        // convert JSON string to Java List
//        LinkedHashMap linked = (LinkedHashMap) results.get(0);//las liked hash map son mapas con orden(como las listas enlazadas)
        LinkedHashMap linked;
        String titulos = "",titulo="";
        for (int i = 0; i < results.size(); i++) {
            linked = (LinkedHashMap) results.get(i);
            titulo = (String) linked.get("title");
            titulos+=linked.get("title")+"\n";
            System.out.println("title: " + titulo);
        }
        //linked.get("title");
        //System.out.println("" + linked.get("title"));
        System.out.println("results: " + results.size());
        //ArrayList results =(ArrayList) new ObjectMapper().readValue(reul, new TypeReference<ArrayList>(){});

        //System.out.println(results.size());
        //System.out.println("About: "+map.get("Abstract"));
        //return results.size();
        //FALTA QUE DEUVELVA QUE PELICULAS están en results
        return titulos;
        //return getField(map,"results").length();
    }

}
