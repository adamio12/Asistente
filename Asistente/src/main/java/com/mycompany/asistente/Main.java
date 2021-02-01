/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.asistente;

import java.awt.SystemColor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

/**
 *
 * @author danko
 */
public class Main {

    public static void main(String[] args) {

        Busqueda.hello();
        try {
            menu();

            /*
            Busqueda.busqueda(map);
            String json = Busqueda.sendHttpGETRequest("Amazon");
            Map jsonMap = Busqueda.abstracto(json);
            jsonMap.get("Abstract ");
             */
 /*
            for (String token : tokens) {
            System.out.println(token);
            }
             */
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void menu() throws IOException {
        String sEntrada, menu = "ask someting or type exit";
        boolean exit = false;

        //Loading the NER - Person model       
        InputStream inputStream = new FileInputStream("en-ner-person.bin");
        TokenNameFinderModel nFModel = new TokenNameFinderModel(inputStream);//name finder model

        //sentence model
        InputStream senInputStream = new FileInputStream("en-sent.bin");
        SentenceModel senModel = new SentenceModel(senInputStream); //sentence model

        //Instantiating the NameFinder class 
        NameFinderME nameFinder = new NameFinderME(nFModel);

        //Instantiating the SentenceDetectorME class 
        SentenceDetectorME detector = new SentenceDetectorME(senModel);

        // instancia para entrada de datos
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //Ya tenemos el "lector"
        while (!exit) {
            System.out.println(menu);
            sEntrada = (String) JOptionPane.showInputDialog(null, menu);
            //sEntrada = br.readLine(); //String entrada
            String question = "search old is the sun";
            SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
            //Loading sentence detector model 
            String tokens[] = tokenizer.tokenize(sEntrada);
            Span spTokens[] = tokenizer.tokenizePos(sEntrada);
            int u = 0, d = tokens.length;
            for (String s : tokens) {
                System.out.println(s.toString() + "<" + u + "," + d + ">");
                u++;
                d--;
            }
            if (tokens[0].equals("describe")) {
                String resultado = Busqueda.busqueda(sEntrada.substring(spTokens[0].getEnd()));
                String frases[] = detector.sentDetect(resultado);
                String res = "";
                for (String sent : frases) {
                    res += sent + "\n";
                }

                JOptionPane.showMessageDialog(null, res);

                System.out.println(resultado);

            } else if (tokens[0].equals("how") && tokens[1].equals("many")
                    && tokens[tokens.length - 4].equals("movies") && tokens[tokens.length - 3].equals("are")
                    && tokens[tokens.length - 2].equals("there") && tokens[tokens.length - 1].equals("?")) {
                String query = "";
                System.out.println("cuantas pelis?");
                for (int i = 2; i <= tokens.length - 5; i++) {
                    query = query + "%20" + tokens[i];
                    System.out.println("" + tokens[i]);
                    System.out.println("mi query: " + query);
                }
                //int resultado = Busqueda.cuantaPeli(sEntrada.substring(spTokens[2].getStart(), spTokens[spTokens.length-5].getEnd()));
                int resultado = Busqueda.cuantaPeli(query);
                System.out.println("there are " + resultado + " movies");
                JOptionPane.showMessageDialog(null, "there are " + resultado + " movies");

            } else if (sEntrada.equals("exit")) {
                System.out.println("cerrando");
                exit = true;
            } else if (tokens[0].equals("which") 
                    && tokens[tokens.length - 4].equals("movies") 
                    && tokens[tokens.length - 3].equals("are")
                    && tokens[tokens.length - 2].equals("there") && tokens[tokens.length - 1].equals("?")){
                
                 String query = "";
                System.out.println("cuales pelis?");
                for (int i = 1; i <= tokens.length - 5; i++) {
                    query = query + "%20" + tokens[i];
                    System.out.println("" + tokens[i]);
                    System.out.println("mi query: " + tokens[i]);
                }
                System.out.println("Mi query "+query);
                //int resultado = Busqueda.cuantaPeli(sEntrada.substring(spTokens[2].getStart(), spTokens[spTokens.length-5].getEnd()));
                String resultado = Busqueda.cualespelis(query);
                System.out.println("there are " + resultado );
                JOptionPane.showMessageDialog(null, "there are:\n" + resultado);
            
            }
        }
    }
}
