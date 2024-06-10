package com.umj.memoramadef;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView ima1, ima2, ima3, ima4, ima5, ima6;
    private TextView texto;
    private int co, puntuas = 0;
    private boolean[] boo, par, primera;
    private String X = "", Y = "";
    private ImageView primeraImagen, segundaImagen;
    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ima1 = (ImageView) findViewById(R.id.imageView8);
        ima2 = findViewById(R.id.imageView9);
        ima4 = findViewById(R.id.imageView11);
        ima5 = findViewById(R.id.imageView12);
        texto = findViewById(R.id.textView2);
        boo = new boolean[4];
        par = new boolean[4];
        primera = new boolean[4];
        for (int i = 0; i < boo.length; i++) {
            boo[i] = false;
            par[i] = false;
            primera[i] = false;
        }

        String a = imagenes();
        co = 0;

        ima1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionado(0, a, ima1);
            }
        });
        ima2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionado(1, a, ima2);
            }
        });

        ima4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionado(2, a, ima4);
            }
        });
        ima5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionado(3, a, ima5);
            }
        });

    }

    private void seleccionado(int index, String a, ImageView imagen) {
        if (!par[index]) {
            co++;
            char[] hola = a.toCharArray();
            if (X.equals("")) {
                X = String.valueOf(hola[index]);
                primera[index] = true;
                primeraImagen = imagen;
                colocar(a, imagen.getId(), index);
                imagen.setEnabled(false);
            } else {
                Y = String.valueOf(hola[index]);
                segundaImagen = imagen;
                colocar(a, imagen.getId(), index);
                if (coparar(X, Y)) {
                    puntuas += 50;
                    par[index] = true;
                    par[primerIdx()] = true;
                    texto.setText("Puntuacion: " + puntuas);
                    resetEstado();
                    if (todosParesEncontrados()) {
                        texto.setText("Puntuacion: " + String.valueOf(puntuas) + " \nFelicidades, econstraste todos los pares :3");
                    }
                } else {
                    puntuas -= 50;
                    texto.setText("Puntuacion: " + puntuas);
                    Handler time = new Handler();
                    time.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resetear();
                        }
                    }, 500);
                }
                co = 0;
            }
        }
    }

    private String imagenes() {
        List<Character> letras = new ArrayList<>();
        letras.add('a');
        letras.add('a');
        letras.add('b');
        letras.add('b');

        Collections.shuffle(letras, new Random());
        String ho = "";
        for (Character letra : letras) {
            ho += String.valueOf(letra);
        }
        return ho;
    }

    private void colocar(String cadena, int idima, int index) {
        char[] hola = cadena.toCharArray();
        int id = 0;
        switch (hola[index]) {
            case 'a':
                id = R.drawable.nijika;
                break;
            case 'b':
                id = R.drawable.royuwall;
                break;

        }
        switch (index) {
            case 0:
                ima1.setImageResource(id);
                break;
            case 1:
                ima2.setImageResource(id);
                break;

            case 2:
                ima4.setImageResource(id);
                break;
            case 3:
                ima5.setImageResource(id);
                break;

        }
    }

    private boolean coparar(String x, String y) {
        return x.equals(y);
    }

    private void resetear() {
        co = 0;
        resetearImagen(ima1, 0);
        resetearImagen(ima2, 1);
        resetearImagen(ima4, 2);
        resetearImagen(ima5, 3);
        resetEstado();
    }

    private void resetearImagen(ImageView imagen, int index) {
        if (!par[index]) {
            imagen.setImageResource(R.drawable.ic_notification);
            imagen.setEnabled(true);
        } else {
            imagen.setEnabled(false);
        }
    }

    private int primerIdx() {
        for (int i = 0; i < primera.length; i++) {
            if (primera[i]) {
                return i;
            }
        }
        return -1;
    }

    private void resetEstado() {
        primeraImagen = null;
        segundaImagen = null;
        X = "";
        Y = "";
        for (int i = 0; i < primera.length; i++) {
            primera[i] = false;
        }
    }

    private boolean todosParesEncontrados() {
        for (boolean b : par) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nose) {
            mensage();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    public void mensage() {
        AlertDialog.Builder dialogoAjustes = new AlertDialog.Builder(MainActivity .this);
        dialogoAjustes.setTitle("Cambiar Cantidad de Cartas OwO");
        dialogoAjustes.setItems(R.array.cuatro, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           switch (which){

               case 0:
                   Intent i = new Intent(MainActivity.this,seis.class);
                   startActivity(i);
                   break;
               case 1:
                   Intent o = new Intent(MainActivity.this, ocho.class);
                   startActivity(o);
                   break;
           }
            }
        });
        AlertDialog cuadro = dialogoAjustes.create();
        cuadro.show();
}
}
