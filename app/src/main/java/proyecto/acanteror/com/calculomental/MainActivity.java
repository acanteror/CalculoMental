package proyecto.acanteror.com.calculomental;


import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {

    private GestureLibrary libreria;
    private TextView info, resultado, num1, num2;
    private Button generarBtn;

    int n1, n2, result;
    boolean correcto = false;

    private GestureOverlayView decenasgv;
    private GestureOverlayView unidadesgv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = (TextView) findViewById(R.id.info);
        //resultado = (TextView) findViewById(R.id.resultado);

        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        generarBtn = (Button) findViewById(R.id.generar);
        generarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generaOperación();



            }
        });


        libreria = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!libreria.load()){
            info.setText("No se pudo cargar libreria");
        } else {
            info.setText("Libreria cargada. Escribe un número");
        }

        GestureOverlayView gv = (GestureOverlayView) findViewById(R.id.GV);
        gv.addOnGesturePerformedListener(this);


    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        result = 0;
        double coincidencia = 0;
        String name ="";

        //resultado.setText("");
        ArrayList<Prediction> predictions = libreria.recognize(gesture);
        for (Prediction prediction : predictions){
            if (prediction.score > coincidencia){
                coincidencia = prediction.score;
                name = prediction.name;
            }
        }
        result = Integer.valueOf(name);
        //resultado.append("" + result);

        comprobarResultado();
        if (correcto == true){
            generaOperación();
            //resultado.setText("");
        } else {
            //resultado.setText("");
        }
    }

    public void comprobarResultado (){
        if (n1+n2 == result){
            Toast.makeText(getApplicationContext(), "Correcto!!", Toast.LENGTH_SHORT).show();
            correcto = true;
        } else {
            Toast.makeText(getApplicationContext(), "Ups! vuelve a intentarlo", Toast.LENGTH_SHORT).show();
            correcto = false;
        }
    }

    public void generaOperación() {

        int maxSuma = 9;
        Random random = new Random();
        n1 = random.nextInt(maxSuma);
        n2 = random.nextInt(maxSuma-n1);
        num1.setText(Integer.toString(n1));
        num2.setText(Integer.toString(n2));

    }


}
