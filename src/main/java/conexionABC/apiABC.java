/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionABC;

import java.util.ArrayList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

/**
 *
 * @author Cesar Casaretto
 */
//interface Payment {
//    public void pay();
//}
public class apiABC {

    public String Noticias(String texto) {
        ArrayList<String> lista = new ArrayList<>();
        try {
            String url = "https://www.abc.com.py/buscar/" + texto;
            Document doc;
            doc = Jsoup.connect(url).get();
            Elements newsHeadlines = doc.select("#fusion-metadata");
            String respuesta1 = newsHeadlines.toString();
            String values[] = respuesta1.split(";");
            String noticias = values[4];
            noticias = noticias.replace("Fusion.globalContent=", "");
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(noticias);
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonArray listaJson = jsonObject.getAsJsonArray("data");
            String vacio = listaJson.toString();
            for (int i = 0; i < listaJson.size(); i++) {
                JsonElement datos = listaJson.get(i);
                JsonObject data = datos.getAsJsonObject();
                JsonElement fecha = data.get("publish_date");
                String fecha2 = fecha.toString().replaceAll("\"", "");
                JsonElement enlace = data.get("_website_urls");
                String link = enlace.toString().replace("[", "").replace("]", "").replaceAll("\"", "");
                String websiteUrls = "https://www.abc.com.py" + link;
                JsonElement promo_items = data.get("promo_items");
                JsonObject enlaceFoto1 = promo_items.getAsJsonObject();
                JsonElement basic = enlaceFoto1.get("basic");
                JsonObject enlaceFoto2 = basic.getAsJsonObject();
                JsonElement resized_urls = enlaceFoto2.get("resized_urls");
                JsonObject enlaceFoto3 = resized_urls.getAsJsonObject();
                JsonElement imagen = enlaceFoto3.get("910x590");
                String imagen1 = imagen.toString().replaceAll("\"", "");

                JsonElement titulo = data.get("headlines");
                JsonObject titulo1 = titulo.getAsJsonObject();
                JsonElement titulo2 = titulo1.get("basic");
                String titulo3 = titulo2.toString().replaceAll("\"", "");

                JsonElement resumen = data.get("subheadlines");
                JsonObject resumen1 = resumen.getAsJsonObject();
                JsonElement resumen2 = resumen1.get("basic");
                String resumen3 = resumen2.toString().replaceAll("\"", "");

                System.out.println("respuesta1: " + data);
                lista.add("{\n"
                        + "    \"fecha\":\"" + fecha2 + "\",\n"
                        + "    \"enlace\":\"" + websiteUrls + "\",\n"
                        + "    \"enlace_foto\":\"" + imagen1 + "\",\n"
                        + "    \"titulo\":\"" + titulo3 + "\",\n"
                        + "    \"resumen\":\"" + resumen3 + "\"\n"
                        + "}");

            }
            System.out.println("Json de consulta: " + lista);
        } catch (IOException ex) {
            Logger.getLogger(apiABC.class.getName()).log(Level.SEVERE, null, ex);
        }
        String Json = lista.toString();
        return Json;
    }
    
    
    

}
