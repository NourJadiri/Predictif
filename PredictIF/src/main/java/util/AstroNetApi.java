package util;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class AstroNetApi {

    private static final String ASTRO_API_KEY = "ASTRO-01-M0lGLURBU0ktQVNUUk8tQjAx"; // (ASTRO-01)

    private static final String ENCODING_UTF8 = "UTF-8";
    private static final SimpleDateFormat JSON_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final String ASTRO_API_URL = "https://servif.insa-lyon.fr/WebDataGenerator/Astro";


    /*
     * Constructeur
     */
    public AstroNetApi() {
    }

    /*
     * Méthode pour appeler le Service Web Profil
     */
    public List<String> getProfil(String prenom, Date dateNaissance) throws IOException {

        ArrayList<String> result = new ArrayList<>();

        JsonObject response = this.post(ASTRO_API_URL,
                new BasicNameValuePair("service", "profil"),
                new BasicNameValuePair("key", ASTRO_API_KEY),
                new BasicNameValuePair("prenom", prenom),
                new BasicNameValuePair("date-naissance", JSON_DATE_FORMAT.format(dateNaissance))
        );

        JsonObject profil = response.get("profil").getAsJsonObject();

        result.add(profil.get("signe-zodiaque").getAsString());
        result.add(profil.get("signe-chinois").getAsString());
        result.add(profil.get("couleur").getAsString());
        result.add(profil.get("animal").getAsString());

        return result;
    }

    /*
     * Méthode pour appeler le Service Web Prédictions
     */
    public List<String> getPredictions(String couleur, String animal, int amour, int sante, int travail) throws IOException {

        ArrayList<String> result = new ArrayList<>();

        JsonObject response = this.post(ASTRO_API_URL,
                new BasicNameValuePair("service", "predictions"),
                new BasicNameValuePair("key", ASTRO_API_KEY),
                new BasicNameValuePair("couleur", couleur),
                new BasicNameValuePair("animal", animal),
                new BasicNameValuePair("niveau-amour", Integer.toString(amour)),
                new BasicNameValuePair("niveau-sante", Integer.toString(sante)),
                new BasicNameValuePair("niveau-travail", Integer.toString(travail))
        );

        //System.out.println(response.get("profil-valide").getAsBoolean());
        result.add(response.get("prediction-amour").getAsString());
        result.add(response.get("prediction-sante").getAsString());
        result.add(response.get("prediction-travail").getAsString());

        return result;
    }

    /*
     * Méthode interne pour réaliser un appel HTTP et interpréter le résultat comme Objet JSON
     */
    protected JsonObject post(String url, NameValuePair... parameters) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        JsonElement responseElement = null;
        JsonObject responseContainer = null;
        Integer responseStatus = null;

        try {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(parameters), ENCODING_UTF8));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {

                responseStatus = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    JsonReader jsonReader = new JsonReader(new InputStreamReader(entity.getContent(), ENCODING_UTF8));
                    try {

                        JsonParser parser = new JsonParser();
                        responseElement = parser.parse(jsonReader);

                    } finally {
                        jsonReader.close();
                    }
                }

            } finally {
                response.close();
            }

            if (responseStatus != null && responseStatus == 200 && responseElement != null) {
                responseContainer = responseElement.getAsJsonObject();
            }
        } catch (UnknownHostException ex) {
            throw new AstroNetApiIOException("Service Request FAILED: Could NOT CONNECT to remote Server ~~> check target URL ???" + "\n******** Target URL =>  " + url + "  <= ********" + "\n");
        } catch (HttpHostConnectException ex) {
            throw new AstroNetApiIOException("Service Request FAILED: Could NOT CONNECT to remote Server ~~> check target URL ???" + "\n******** Target URL =>  " + url + "  <= ********" + "\n");
        } catch (IllegalStateException ex) {
            throw new AstroNetApiIOException("Service Request FAILED: Wrong HTTP Response FORMAT - not a JSON Object ~~> check target URL output ???" + "\n******** Target URL =>  " + url + "  <= ********" + "\n**** Parameters:\n" + AstroNetApiIOException.debugParameters(" * ", parameters));
        }

        httpclient.close();

        if (responseContainer == null) {
            String statusLine = "???";
            if (responseStatus != null) {
                statusLine = responseStatus.toString();
                if (responseStatus == 400) {
                    statusLine += " - BAD REQUEST ~~> check request parameters ???";
                }
                if (responseStatus == 404) {
                    statusLine += " - NOT FOUND ~~> check target URL ???";
                }
                if (responseStatus == 500) {
                    statusLine += " - INTERNAL SERVER ERROR ~~> check target Server Log ???";
                }
            }
            throw new AstroNetApiIOException("Service Request FAILED with HTTP Error " + statusLine + "\n******** Target URL =>  " + url + "  <= ********" + "\n**** Parameters:\n" + AstroNetApiIOException.debugParameters(" * ", parameters));
        }

        return responseContainer;
    }

    static public class AstroNetApiIOException extends IOException {

        public AstroNetApiIOException(String message) {
            super(message);
        }

        public AstroNetApiIOException(String message, Throwable cause) {
            super(message, cause);
        }

        public AstroNetApiIOException(Throwable cause) {
            super(cause);
        }

        public static String debugParameters(String alinea, NameValuePair... parameters) {

            StringBuilder debug = new StringBuilder();

            for (NameValuePair parameter : parameters) {
                debug.append(alinea).append(parameter.getName()).append(" = ").append(parameter.getValue()).append("\n");
            }

            return debug.toString();
        }

    }

}
