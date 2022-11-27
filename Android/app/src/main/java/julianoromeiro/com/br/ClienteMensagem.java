package sistema_alagamento;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ClienteMensagem {


    private static String readStream(InputStream in) {
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;

        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('n');
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return total.toString();
    }

    private static String request(String stringUrl) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }

    public static Data  retornarmensagemclima(String dataconsulta, int horaconsulta) throws JSONException {
        String responseBody = request("http://portal.c-trends.com.br/temporario/Service1.asmx/SERV_LOCALIZAR_MENSAGEM?Data=" + dataconsulta + "&Hora=" + horaconsulta);
        // JSONObject obj = new JSONObject(responseBody);
        String data="", hora="", mensagem="";

        JSONObject trendLists = null;
        trendLists = new JSONObject(responseBody);
        JSONArray jsonCarros = null;
        jsonCarros = trendLists.getJSONArray("Table");

        for (int i = 0; i < jsonCarros.length(); i++) {
            JSONObject jsonCarro = jsonCarros.getJSONObject(i);
            data = jsonCarro.optString("Data");
            hora = jsonCarro.optString("Hora");
            mensagem = jsonCarro.optString("Mensagem");

        }

        return new Data(data, hora, mensagem);

    }

}

