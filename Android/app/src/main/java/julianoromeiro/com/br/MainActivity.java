package sistema_alagamento;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.time.LocalDate;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    TextView lblData, lblHora, lblMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permite conexão com a Internet na Thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        pedirPermissoes();


        lblData = (TextView) findViewById(R.id.lblData);
        lblHora = (TextView) findViewById(R.id.lblHora);
        lblMensagem = (TextView) findViewById(R.id.lblMensagem);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //     Intent intent1 = new Intent(getApplication(), login2.class);
            //   startActivity(intent1);
            finish();

            return;
        }
    }

    public void carregarMensagem(){

        try {

            LocalDate localDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                localDate = LocalDate.now();
            }

            String dataconsulta = localDate.toString();
            Calendar data = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
            int hora = data.get(Calendar.HOUR_OF_DAY);
            int min = data.get(Calendar.MINUTE);
            int seg = data.get(Calendar.SECOND);
            Data msg = ClienteMensagem.retornarmensagemclima(dataconsulta, hora);
            lblData = (TextView) findViewById(R.id.lblData);
            lblHora = (TextView) findViewById(R.id.lblHora);
            lblMensagem = (TextView) findViewById(R.id.lblMensagem);
            lblData.setText("Data: " + msg.getData());
            lblHora.setText("Hora: " + msg.getHora() + ":" + min + ":" + seg);
            lblMensagem.setText("Mensagem: " + msg.getMensagem());

        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void pedirPermissoes() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        } else
            carregarMensagem();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //        carregarMensagem();
                } else {
                    Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                    pedirPermissoes();
                }
               return;
            }
        }
    }


}