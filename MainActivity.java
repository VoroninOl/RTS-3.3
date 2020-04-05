package com.example.lab3_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static int a;
    public static int b;
    public static int c;
    public static int d;
    public static int y;
    public static double mutchance = 0.1;
    public static int len = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    static int[] born(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = (int)(24.5*Math.random()+1);
        }
        return a;
    }
    static int[] roulette(int[][] x, double[] chance) {
        double c = Math.random();
        int r = 0;
        if (c < chance[0]) {
            r = 0;
        }
        if ((c >= chance[0]) & (c < chance[0] + chance[1])){
            r = 1;
        }
        if ((c >= chance[0] + chance[1]) & (c < chance[0] + chance[1] + chance[2])){
            r = 2;
        }
        if ((c >= chance[0] + chance[1] + chance[2]) & (c <= chance[0] + chance[1] + chance[2] + chance[3])){
            r = 3;
        }
        return x[r];
    }
    static int mutationChance(int x) {
        double chance = Math.random();
        if (chance < mutchance) {
            x = (int)(8.5*Math.random()+1);
        }
        return x;
    }
    static boolean arrayEqual(int[] x1,int[] x2) {
        boolean equal = false;
        int c = 0;
        for(int i = 0; i < x1.length; i++) {
            if (x1[i] == x2[i]) {
                c++;
            }
        }
        if (c == x1.length) {
            equal = true;
        }
        return equal;
    }
    static int[][] newGen(int[][] x,double[] chance) {
        java.util.Random random = new java.util.Random();
        int x1[] = roulette(x, chance);
        int x2[] = roulette(x, chance);
        if (arrayEqual(x1, x2)){
            while(arrayEqual(x1,x2)) {
                x2 = roulette(x, chance);
            }
        }
        int dif = random.nextInt(3) + 1;
        int[] newgen1 = new int[x1.length];
        int[] newgen2 = new int[x1.length];
        for (int i = 0; i < x1.length; i++) {
            if (i < dif) {
                newgen1[i] = x1[i];
                newgen1[i] = mutationChance(newgen1[i]);
                newgen2[i] = x2[i];
                newgen2[i] = mutationChance(newgen2[i]);
            }
            if (i >= dif) {
                newgen1[i] = x2[i];
                newgen1[i] = mutationChance(newgen1[i]);
                newgen2[i] = x1[i];
                newgen2[i] = mutationChance(newgen2[i]);
            }
        }
        int[][] newgen = {newgen1, newgen2};
        return newgen;
    }
    static int[] findWinner(int[][] x) {
        int[] winner = null;
        if (fitness(x[0])==0){
            winner = x[0];
        }
        if (fitness(x[1])==0){
            winner = x[1];
        }
        if (fitness(x[2])==0){
            winner = x[2];
        }
        if (fitness(x[3])==0){
            winner = x[3];
        }
        return winner;
    }
    static int fitness(int[] x) {
        int fit = a*x[0]+b*x[1]+c*x[2]+d*x[3];
        fit = Math.abs((y - fit));
        return fit;
    }
    static int checkWinner(){
        int[] x1 = new int[len], x2 = new int[len], x3 = new int[len], x4 = new int[len];
        boolean stop = false;
        int[] winner = null;
        x1 = born(x1);
        x2 = born(x2);
        x3 = born(x3);
        x4 = born(x4);
        int [][] x = {x1, x2, x3, x4};
        if ((fitness(x1)==0) | (fitness(x2)==0) | (fitness(x3)==0) | (fitness(x4)==0)){
            winner = findWinner(x);
            stop = true;
        }
        int generationNumber = 1;
        while(stop == false) {
            double variation = 1/(double)fitness(x1) + 1/(double)fitness(x2) + 1/(double)fitness(x3) + 1/(double)fitness(x4);
            double chance1 = (1/(double)fitness(x1))/variation;
            double chance2 = (1/(double)fitness(x2))/variation;
            double chance3 = (1/(double)fitness(x3))/variation;
            double chance4 = (1/(double)fitness(x4))/variation;
            double[] chance = {chance1, chance2, chance3, chance4};
            int[][] halfx1 = newGen(x, chance);
            int[][] halfx2 = newGen(x, chance);
            x1 = halfx1[0];
            x2 = halfx1[1];
            x3 = halfx2[0];
            x4 = halfx2[1];
            x[0] = x1;
            x[1] = x2;
            x[2] = x3;
            x[3] = x4;
            if ((fitness(x1)==0) | (fitness(x2)==0) | (fitness(x3)==0) | (fitness(x4)==0)){
                winner = findWinner(x);
                stop = true;
            }
            generationNumber++;
        }
        return generationNumber;
    }
    public void onButton(View v){
        EditText numA = (EditText)findViewById(R.id.numA);
        EditText numB = (EditText)findViewById(R.id.numB);
        EditText numC = (EditText)findViewById(R.id.numC);
        EditText numD = (EditText)findViewById(R.id.numD);
        EditText numY = (EditText)findViewById(R.id.numY);
        TextView x1res = (TextView)findViewById(R.id.x1res);
        TextView genNum = (TextView)findViewById(R.id.genNum);

        a = Integer.parseInt(numA.getText().toString());
        b = Integer.parseInt(numB.getText().toString());
        c = Integer.parseInt(numC.getText().toString());
        d = Integer.parseInt(numD.getText().toString());
        y = Integer.parseInt(numY.getText().toString());
        int[] genArray = new int[90];
        for (int i = 0; i < 90; i++){
            genArray[i] = checkWinner();
            mutchance = mutchance + 0.01;
        }
        int min = genArray[0];
        int counter = 0;
        for (int i = 0; i<genArray.length; i++) {
            if (genArray[i]<min) {
                min = genArray[i];
                counter = i;
            }
        }
        double bestMutation = 0.05 + ((double)counter)/100;
        x1res.setText(Double.toString(bestMutation));
        genNum.setText(Integer.toString(min));
    }
}
