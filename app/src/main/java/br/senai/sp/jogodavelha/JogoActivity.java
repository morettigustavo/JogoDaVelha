package br.senai.sp.jogodavelha;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class JogoActivity extends AppCompatActivity {

    LinearLayout llPrincipal, llSecundario, llTerciario;
    GridLayout glTabuleiro;
    TextView tvEscolha, tvJogador, tvVitoriasX, tvVitoriasO, tvVelha;
    Button btnReiniciar, btnZerar;
    RadioGroup rgEscolha;
    RadioButton rbX, rbO;
    ImageView ivJogador, aivTabuleiro[][];
    boolean bJogar = true;
    AlertDialog alerta;
    int iJogadas[][], iPlacar[] = {0, 0, 0}, iJogador = 0, ii, i, iVisibilidade = View.INVISIBLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        criarLayout();
        trocarLaytout();
        setContentView(llPrincipal);
    }

    private void criarLayout() {
        Intent intentRecebe = getIntent();
        iPlacar[0] = intentRecebe.getIntExtra("xix", 0);
        iPlacar[1] = intentRecebe.getIntExtra("bola", 0);
        iPlacar[2] = intentRecebe.getIntExtra("velha", 0);
        LinearLayout.LayoutParams mm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams ww = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams wwc = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);wwc.gravity = Gravity.CENTER;
        LinearLayout.LayoutParams mw = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams wwcm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        wwcm.gravity = Gravity.CENTER;
        wwcm.setMargins(0, 10, 0, 10);
        llPrincipal = new LinearLayout(JogoActivity.this);
        llPrincipal.setLayoutParams(mm);
        llPrincipal.setOrientation(LinearLayout.VERTICAL);
        llPrincipal.setPadding(15, 15, 15, 15);
        tvEscolha = new TextView(JogoActivity.this);
        tvEscolha.setLayoutParams(wwc);
        tvEscolha.setText("Escolha quem jogará 1º:");
        tvEscolha.setTextSize(20);
        tvEscolha.setTextColor(Color.parseColor("#000000"));
        llPrincipal.addView(tvEscolha);
        rgEscolha = new RadioGroup(JogoActivity.this);
        rgEscolha.setLayoutParams(mw);
        rgEscolha.setOrientation(RadioGroup.HORIZONTAL);
        rgEscolha.setGravity(Gravity.CENTER);
        rbX = new RadioButton(JogoActivity.this);
        rbX.setCompoundDrawablesWithIntrinsicBounds(R.drawable.xix, 0, 0, 0);
        rbX.setLayoutParams(ww);
        rbX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivJogador.setImageResource(R.drawable.xix);
                trocarLaytout();
            }
        });
        rgEscolha.addView(rbX);
        rbO = new RadioButton(JogoActivity.this);
        rbO.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bolinha, 0, 0, 0);
        rbO.setLayoutParams(ww);
        rbO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivJogador.setImageResource(R.drawable.bolinha);
                iJogador = 1;
                trocarLaytout();
            }
        });
        rgEscolha.addView(rbO);
        llPrincipal.addView(rgEscolha);
        llSecundario = new LinearLayout(JogoActivity.this);
        llSecundario.setLayoutParams(ww);
        llSecundario.setOrientation(LinearLayout.HORIZONTAL);
        tvJogador = new TextView(JogoActivity.this);
        tvJogador.setLayoutParams(ww);
        tvJogador.setTextSize(20);
        tvJogador.setTextColor(Color.parseColor("#000000"));
        tvJogador.setText("Quem joga agora é: ");
        llSecundario.addView(tvJogador);
        ivJogador = new ImageView(JogoActivity.this);
        ivJogador.setLayoutParams(ww);
        ivJogador.setPadding(30, 0, 0, 0);
        llSecundario.addView(ivJogador);
        llPrincipal.addView(llSecundario);
        iJogadas = new int[5][5];
        aivTabuleiro = new ImageView[5][5];
        for (i = 0; i < 5; i++) {
            for (ii = 0; ii < 5; ii++) {
                aivTabuleiro[i][ii] = new ImageView(JogoActivity.this);
                iJogadas[i][ii] = 0;
                if ((i + ii) % 2 == 0) {
                    if (i % 2 == 0) {
                        aivTabuleiro[i][ii].setImageResource(R.drawable.casa);
                        iJogadas[i][ii] = R.drawable.casa;
                        aivTabuleiro[i][ii].setOnClickListener(new View.OnClickListener() {
                            int iLinha = i, iColuna = ii;
                            @Override
                            public void onClick(View v) {
                                jogar(iLinha, iColuna);
                            }
                        });
                    } else {aivTabuleiro[i][ii].setImageResource(R.drawable.cruz);}
                } else {
                    if (i % 2 == 0) {aivTabuleiro[i][ii].setImageResource(R.drawable.vertical);}
                    else {aivTabuleiro[i][ii].setImageResource(R.drawable.horizontal);}
                }
            }
        }
        glTabuleiro = new GridLayout(JogoActivity.this);
        glTabuleiro.setLayoutParams(wwc);
        glTabuleiro.setPadding(0, 50, 0, 0);
        glTabuleiro.setColumnCount(5);
        glTabuleiro.setRowCount(5);
        for (int i = 0; i < 5; i++) {for (int ii = 0; ii < 5; ii++) {glTabuleiro.addView(aivTabuleiro[i][ii]);}}
        llPrincipal.addView(glTabuleiro);
        llTerciario = new LinearLayout(JogoActivity.this);
        llTerciario.setLayoutParams(mm);
        llTerciario.setOrientation(LinearLayout.HORIZONTAL);
        tvVitoriasX = new TextView(JogoActivity.this);
        tvVitoriasX.setLayoutParams(wwc);
        tvVitoriasX.setText("Vitorias X: " + iPlacar[0]);
        tvVitoriasX.setTextSize(20);
        tvVitoriasX.setTextColor(Color.parseColor("#000000"));
        llPrincipal.addView(tvVitoriasX);
        tvVitoriasO = new TextView(JogoActivity.this);
        tvVitoriasO.setLayoutParams(wwc);
        tvVitoriasO.setText("Vitorias O: " + iPlacar[1]);
        tvVitoriasO.setTextSize(20);
        tvVitoriasO.setTextColor(Color.parseColor("#000000"));
        llPrincipal.addView(tvVitoriasO);
        tvVelha = new TextView(JogoActivity.this);
        tvVelha.setLayoutParams(wwc);
        tvVelha.setText("Velhas: " + iPlacar[2]);
        tvVelha.setTextSize(20);
        tvVelha.setTextColor(Color.parseColor("#000000"));
        llPrincipal.addView(tvVelha);
        btnReiniciar = new Button(JogoActivity.this);
        btnReiniciar.setLayoutParams(wwcm);
        btnReiniciar.setText("REINICIAR");
        btnReiniciar.setTextSize(20);
        btnReiniciar.setTextColor(Color.parseColor("#000000"));
        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpar();
            }
        });
        llPrincipal.addView(btnReiniciar);
        btnZerar = new Button(JogoActivity.this);
        btnZerar.setLayoutParams(wwcm);
        btnZerar.setText("ZERAR");
        btnZerar.setTextSize(20);
        btnZerar.setTextColor(Color.parseColor("#000000"));
        btnZerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetar();
            }
        });
        llPrincipal.addView(btnZerar);
    }

    private void resetar(){
        Intent intencao = new Intent(JogoActivity.this, JogoActivity.class);
        startActivity(intencao);
        finish();
    }

    private void limpar(){
        Intent intencao = new Intent(JogoActivity.this, JogoActivity.class);
        intencao.putExtra("xix", iPlacar[0]);
        intencao.putExtra("bola", iPlacar[1]);
        intencao.putExtra("velha", iPlacar[2]);
        startActivity(intencao);
        finish();
    }

    private void jogar(int iLinha, int iColuna) {
        if (bJogar) {
            if (iJogadas[iLinha][iColuna] == R.drawable.casa) {
                if (iJogador % 2 == 0) {
                    iJogadas[iLinha][iColuna] = R.drawable.xix;
                    ivJogador.setImageResource(R.drawable.bolinha);
                } else {
                    iJogadas[iLinha][iColuna] = R.drawable.bolinha;
                    ivJogador.setImageResource(R.drawable.xix);
                }
                aivTabuleiro[iLinha][iColuna].setImageResource(iJogadas[iLinha][iColuna]);
                iJogador++;
            }
            verificarGanhador();
        }
    }

    private void verificarGanhador() {
        String sGanhador = "O ganhador foi: ";
        int iGanhador = 0;
        int iSomaLinha, iSomaCol, iSomaBarra, iSomaInv, iCasa = 0;
        for (i = 0; i < 5; i++) {
            iSomaLinha = 0;
            iSomaCol = 0;
            for (ii = 0; ii < 5; ii++) {
                if ((i + ii) % 2 == 0 && i % 2 == 0) {
                    iSomaLinha += iJogadas[i][ii];
                    iSomaCol += iJogadas[ii][i];
                }
                if (iJogadas[i][ii] == R.drawable.casa) {iCasa++;}
            }
            iSomaBarra = iJogadas[0][0] + iJogadas[2][2] + iJogadas[4][4];
            iSomaInv = iJogadas[0][4] + iJogadas[2][2] + iJogadas[4][0];
            if(bJogar){
                if (iSomaLinha == 3 * R.drawable.xix || iSomaCol == 3 * R.drawable.xix || iSomaBarra == 3 * R.drawable.xix || iSomaInv == 3 * R.drawable.xix) {
                    iPlacar[0]++;
                    sGanhador = "O ganhador foi:";
                    iGanhador = R.drawable.xix;
                    bJogar = false;
                } else if (iSomaLinha == 3 * R.drawable.bolinha || iSomaCol == 3 * R.drawable.bolinha || iSomaBarra == 3 * R.drawable.bolinha || iSomaInv == 3 * R.drawable.bolinha) {
                    iPlacar[1]++;
                    sGanhador = "O ganhador foi:";
                    iGanhador = R.drawable.bolinha;
                    bJogar = false;
                }
            }
        }
        if (iCasa == 0 && bJogar) {
            iPlacar[2]++;
            sGanhador = "Deu velha";
            bJogar = false;
        }
        if (!bJogar) {
            AlertDialog.Builder criaAlerta = new AlertDialog.Builder(this);
            LayoutInflater li =getLayoutInflater();
            View visual =li.inflate(R.layout.alerta,null);
            ImageView ivGanhador = visual.findViewById(R.id.ivGanhador);
            ivGanhador.setImageResource(iGanhador);
            TextView tvGanhador = visual.findViewById(R.id.tvGanhador);
            tvGanhador.setText(sGanhador);
            visual.findViewById(R.id.btnReiniciar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    limpar();
                }
            });

            visual.findViewById(R.id.btnZerar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetar();
                }
            });

            visual.findViewById(R.id.btnContinuar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alerta.dismiss();
                }
            });
            criaAlerta.setView(visual);
            alerta=criaAlerta.create();
            alerta.show();

        }
        tvVitoriasX.setText("Vitorias X: " + iPlacar[0]);
        tvVitoriasO.setText("Vitorias O: " + iPlacar[1]);
        tvVelha.setText("Velhas: " + iPlacar[2]);
    }

    private void trocarLaytout(){
        tvJogador.setVisibility(iVisibilidade);
        ivJogador.setVisibility(iVisibilidade);
        glTabuleiro.setVisibility(iVisibilidade);
        tvVitoriasX.setVisibility(iVisibilidade);
        tvVitoriasO.setVisibility(iVisibilidade);
        tvVelha.setVisibility(iVisibilidade);
        btnReiniciar.setVisibility(iVisibilidade);
        btnZerar.setVisibility(iVisibilidade);
        if (iVisibilidade == View.VISIBLE){
            tvEscolha.setVisibility(View.GONE);
            rgEscolha.setVisibility(View.GONE);
        }
        iVisibilidade = View.VISIBLE;
    }
}