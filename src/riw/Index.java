/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 *
 * @author ilyes
 */
public class Index {
    List <String> ar = new ArrayList<String>();
    List <String> nr = new ArrayList<String>();
    List <String> cr = new ArrayList<String>();
    ArrayList<String> itr = new ArrayList<String>();
    static LinkedList<String> sns = new LinkedList();
    static LinkedList<String> ss = new LinkedList();
    public void normalisation(File f) throws FileNotFoundException, IOException {
            
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        
        String ch;
        while((ch = br.readLine()) != null){
            
            nr.add(ch.toLowerCase());
            
        }
        br.close();
        fr.close();
    
    }
    public void tokenisation() throws FileNotFoundException, IOException{
        
        String [] str;
        int n = 0;
        while(n < nr.size()){
            str = nr.get(n).split("[\\s+,()«»'’;.]");
            int i = 0;
            while(i < str.length){
                ar.add(str[i]);
                i++;
            }
            n++;
        }
        nr.clear();
        for(int j = 0;j < ar.size();j++){
            if(ar.get(j).isEmpty()){
                ar.remove(j);
            }
        }
    }
    
    public void elemination(File m_vid, File sortie) throws FileNotFoundException, IOException{
        
        int i = 0;
        String t;
        while(i < ar.size()){
            FileReader frm = new FileReader(m_vid);
            BufferedReader brm = new BufferedReader(frm);
            while((t=brm.readLine()) != null){
                if(ar.get(i).compareTo(t) == 0){
                    ar.set(i,"");
                }
            }
            i++;
        }
        
        supprimer(ar);
        cr.addAll(ar);
        
        int j = 0;
        while( j < cr.size()){
            cr.set(j, cr.get(j).toString().replaceAll("\\p{C}", ""));
            j++;
        }
        
        FileWriter fw = new FileWriter(sortie);
        BufferedWriter wr = new BufferedWriter(fw);
        
        int a = 0;
        supprimer(ar);
        while(a<ar.size()){
            
            fw.write(ar.get(a).toLowerCase());
            fw.write(" ");
            a++;
        }
        
        fw.close();
        wr.close();
        ar.clear();
        supprimer(cr);
    }
    
    public void supprimer(List <String> a){
        for(int j = 0;j < a.size();j++){
            if(a.get(j).equals("")){
                a.remove(j);
            }
        }
    }

    public void index_brute(File r_f,TextArea tr) throws FileNotFoundException, IOException{
        
        supprimer(cr);
        
        int i = 0;
        while(i < cr.size()){
            for(File f : r_f.listFiles()){
                FileReader fr = new FileReader(f);
                Scanner sc = new Scanner(fr);
                while(sc.hasNext()){
                    if(sc.next().equals(cr.get(i))){
                        itr.add(cr.get(i) + " ---> " + f.getName());
                    }
                }
                fr.close();
                sc.close();
            }
            i++;
        }
        
        int j = 0;
        while(j <  itr.size()){
            //System.out.println(itr.get(j));
            tr.appendText(itr.get(j));
            tr.appendText("\n");
            j++;
        }
        
    }
    
    public void liste_postage(File r_f,TextArea tr) throws FileNotFoundException, IOException{
        
        int i = 0;
        Set<String> s = new LinkedHashSet<>(cr);
        s.addAll(cr);
        cr.clear();
        cr.addAll(s);
        
        while(i < cr.size()){
            LinkedList ind = new LinkedList();
            ind.add(cr.get(i));
            for(File f : r_f.listFiles()){
                
                FileReader fr = new FileReader(f);
                Scanner sc = new Scanner(fr);
                while(sc.hasNext()){
                    if(sc.next().equals(cr.get(i)) && (!ind.contains(f.getName()))){
                        ind.add(f.getName());
                    }
                }
                fr.close();
                sc.close();
            }
            tr.appendText(ind.toString());
            tr.appendText("\n");
            //System.out.println(ind);
            i++;
        }
    }
    
    public void requete(File r_f,StackPane root ,String s) throws FileNotFoundException, IOException{
        int i = 0;
        Set<String> tes = new LinkedHashSet<>();
        ArrayList st = new ArrayList<String>();
        ArrayList<String> avant = new ArrayList();
        ArrayList <String>frs = new ArrayList<String>();
        String []soun =s.split(" ou ");
        
        String [] str = null;
        if(s.contains(" ou ")){
            str = s.split(" ou ");
        }
        else{
            str = new String [1];
            str[0]= s;    
        }
        int k =0;
        while(k<str.length){
            str[k]=""+str[k]+"";
            k++;
        }
        while(i<str.length){
            for(File f : r_f.listFiles()){
                String t;
                String tx = "";
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                while((t = br.readLine()) != null){
                    tx = tx+" "+t;
                }
                if( str[i].contains(" et ")){
                        String []spl = str[i].split(" et ");
                        
                        int ts = 0;
                        while(ts < spl.length-1){
                            if(tx.contains(spl[ts]) && tx.contains(spl[ts+1])){
                                st.add(f.getName());
                            }
                            else{
                                int n = 0;
                                LinkedList <String>ls = new LinkedList();
                                while(n < spl.length){
                                    soundex(spl[n],ls,n);
                                    n++;
                                }
                                n = 0;
                                while(n<ls.size()){
                                    if(ls.get(n).matches(".*\\d+.*")){
                                        n++;
                                    }
                                    else{
                                        ls.remove(n);
                                        n++;
                                    }
                                }
                                n = 0;
                                int l =0;
                                ls.add("");
                                while(n< sns.size()){
                                    if(sns.contains(ls.get(l))&& l<=ls.size()-1){
                                        int ind = sns.indexOf(ls.get(l))-1;
                                        avant.add(sns.get(ind));
                                        l++;
                                        
                                    }   
                                    n++;
                                }
                                int av = 0;
                                while(av < avant.size()-1){
                                    if(tx.contains(avant.get(av)) && tx.contains(avant.get(av+1))){
                                        st.add(f.getName());
                                    }
                                    
                                    else{
                                        st.remove(f.getName());
                                    }
                                    av++;
                                int p = 0;
                                Set<String> avs = new LinkedHashSet<>();
                                avs.addAll(avant);
                                avant.clear();
                                avant.addAll(avs);
                                String sp = "";
                                while(p < avant.size()){
                                sp = sp+avant.get(p)+" ";
                                p++;
                                }
                                st.add(0, "Essayer avec: "+sp);
                        
                                }
                                
                                st.remove(f.getName());
                                
                            }
                            ts++;
                        }
                    }
                    else{
                        if(tx.contains(str[i])){
                            st.add(f.getName()); 
                       }
                        else{
                            int n = 0;
                                LinkedList <String>ls = new LinkedList();
                                while(n < str.length){
                                    soundex(str[n],ls,n);
                                    n++;
                                }
                                n = 0;
                                while(n<ls.size()){
                                    if(ls.get(n).matches(".*\\d+.*")){
                                        n++;
                                    }
                                    else{
                                        ls.remove(n);
                                        n++;
                                    }
                                }
                                System.out.println(ls);
                                n = 0;
                                int l =0;
                                ls.add("");
                                while(n< sns.size()){
                                    if(sns.contains(ls.get(l))&& l<=ls.size()-1){
                                        int ind = sns.indexOf(ls.get(l))-1;
                                        avant.add(sns.get(ind));
                                        l++;
                                    }   
                                    n++;
                                }
                                int av = 0;
                                while(av<avant.size()){
                                    if(tx.contains(avant.get(av))){
                                        st.add(f.getName()); 
                                    }
                                    av++;
                                }
                                int p = 0;
                        Set<String> avs = new LinkedHashSet<>();
                        avs.addAll(avant);
                        avant.clear();
                        avant.addAll(avs);
                        String sp = "";
                        while(p < avant.size()){
                            sp = sp+avant.get(p)+" ";
                            p++;
                        }
                        System.out.println(avant);
                        }

                    }
                
                fr.close();
                br.close();
            }
            i++;
        }
        tes.addAll(st);
        st.clear();
        st.addAll(tes);
        int j = 0;
        int pnt = -70;
        while(j<st.size()){
            
            Label l = new Label (st.get(j).toString());
            l.setTranslateX(-0);
            l.setTranslateY(pnt);
            l.setStyle("-fx-font-weight: bold");
            l.setTextFill(Color.web("#ff0000"));
            root.getChildren().add(l);
            pnt = pnt+20;
            j++;
        }
    }
    
    public void fill(){
        int m =0;
        while(m<cr.size()){
            soundex(cr.get(m),sns,m);
            m++;
        }
        System.out.println(sns);
    }
    public void soundex(String c, LinkedList<String> sn,int ind){
    
        int i = 0;
        
            int j = 1;
            String sb = ""+c.charAt(0);
            while(j<c.length()){
                switch(c.charAt(j)) {
                    case 'a' :
                    case 'e' :
                    case 'h' :
                    case 'i' :
                    case 'o' :
                    case 'u' :
                    case 'w' :
                    case 'y' :
                        sb = sb+0; 
                    break;
                    case 'b' :
                    case 'p' :
                        sb =sb+1;
                    break;
                    case 'c' :
                    case 'k' :
                    case 'q' :
                        sb = sb+2;
                    break;
                    case 'd' :
                    case 't' :
                        sb = sb+3;
                    break;
                    case 'l' :
                        sb = sb+4;
                    break;
                    case 'm' :
                    case 'n' :
                        sb = sb+5;
                    break;
                    case 'r' :
                        sb = sb+6;
                    break;
                    case 'g' :
                    case 'j' :
                        sb = sb+7;
                    break;
                    case 'x' :
                    case 'z' :
                    case 's' :    
                        sb = sb+8;
                    break;
                    case 'f' :
                    case 'v' :    
                        sb = sb+9;
                    break;
                    
                    default :
                        
                }
                j++;
            }
            sn.add(" "+c+" ");
            sn.add(" "+sb+" ");
        
        int k = 0;
        while (k<sn.size()){
            int l = 1;
            while(l<sn.get(k).length()-1){
                if(sn.get(k).charAt(l) == sn.get(k).charAt(l+1)){
                    char [] chr = sn.get(k).toCharArray();
                    chr[l] = '0';
                    sn.set(k, String.valueOf(chr));
                }
                l++;
            }
            k++;
        }
        int n = 0;
        while (n<sn.size()){
            if(sn.get(n).contains("0")){
                sn.set(n,sn.get(n).replaceAll("0", ""));
            }
            n++;
        }

    }
    
    public void bgram(){
        int j = 0;
        ArrayList<String> bgr = new ArrayList<String>();
        while(j < cr.size()){
            LinkedList bg = new LinkedList();
            bg.add(cr.get(j));
            int i = 0;
            while(i < cr.get(j).length()-1){
                bg.add(Character.toString(cr.get(j).charAt(i))+
                        Character.toString(cr.get(j).charAt(i+1)));
                i++;
            }
            j++;
            bgr.addAll(bg);
        }
    }
    
    public int dist(String lhs, String rhs){
        
        int len0 = lhs.length() + 1;                                                     
        int len1 = rhs.length() + 1;                                                         
        int[] cost = new int[len0];                                                     
        int[] newcost = new int[len0];                                              
        for (int i = 0; i < len0; i++) 
            cost[i] = i;                                     
        for (int j = 1; j < len1; j++) {                                                
            newcost[0] = j;                                                             
            for(int i = 1; i < len0; i++) {                                             
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;             
                int cost_replace = cost[i - 1] + match;                                 
                int cost_insert  = cost[i] + 1;                                         
                int cost_delete  = newcost[i - 1] + 1;                                  
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }                                                                           
            int[] swap = cost; cost = newcost; newcost = swap;                          
        }                     
         return cost[len0 - 1];   
    }
    
    public void distance(String s,File r_f,StackPane root) throws FileNotFoundException, IOException{
        int i = 0;
        Set<String> tes = new LinkedHashSet<>();
        ArrayList st = new ArrayList<String>();
        ArrayList<String> avant = new ArrayList();
        ArrayList <String>frs = new ArrayList<String>();
        LinkedList <String> avnt = new LinkedList();
        String []soun =s.split(" ou ");
        
        String [] str = null;
        if(s.contains(" ou ")){
            str = s.split(" ou ");
        }
        else{
            str = new String [1];
            str[0]= s;    
        }
        while(i<str.length){
            for(File f : r_f.listFiles()){
                String t;
                String tx = "";
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                while((t = br.readLine()) != null){
                    tx = tx+" "+t;
                }
                if( str[i].contains(" et ")){
                        String []spl = str[i].split(" et ");
                        
                        int ts = 0;
                        while(ts < spl.length-1){
                            if(tx.contains(spl[ts]) && tx.contains(spl[ts+1])){
                            st.add(f.getName());
                            }
                            else{
                                int p = 0;
                                int min=3;
                                
                                int ind=0;
                                while(p<spl.length){
                                    int ps = 0;
                                    while(ps<ss.size()){
                                        if(min >= dist(spl[p],ss.get(ps))){
                                            min = dist(spl[p],ss.get(ps));
                                            ind = ps;
                                        }
                                        else{
                                        
                                        }
                                        ps++;
                                    }
                                    avnt.add(ss.get(ind));
                                    p++;
                                }
                                Set<String> ds = new LinkedHashSet<>();
                                ds.addAll(avnt);
                                avnt.clear();
                                avnt.addAll(ds);
                                int pos = 0;
                                System.out.println(avnt);
                                avnt.add("");
                                while(pos < avnt.size()-1){
                                    if(tx.contains(avnt.get(pos)) && tx.contains(avnt.get(pos+1))){
                                        st.add(f.getName());
                                    }
                                    else{
                                        st.remove(f.getName());
                                    }
                                    pos++;
                                }
                                st.remove(f.getName());
                            }
                            ts++;
                        }
                    }
                    else{
                        if(tx.contains(str[i])){
                            st.add(f.getName()); 
                       }
                        else{
                                int p = 0;
                                int min=3;
                                
                                int ind=0;
                                
                                while(p<str.length){
                                    int ps = 0;
                                    while(ps<ss.size()-1){
                                        if(min >= dist(str[p],ss.get(ps))){
                                            min = dist(str[p],ss.get(ps));
                                            ind = ps;
                                        }
                                        else{
                                            min = min;
                                        }
                                        ps++;
                                    }
                                    avnt.add(ss.get(ind));
                                    p++;
                                }
                                System.out.println(avnt);
                                Set<String> ds = new LinkedHashSet<>();
                                ds.addAll(avnt);
                                avnt.clear();
                                avnt.addAll(ds);
                                int pos = 0;
                                avnt.add("");
                                while(pos < avnt.size()-1){
                                    if(tx.contains(avnt.get(pos))){
                                        st.add(f.getName());
                                    }
                                    pos++;
                                }
                        }

                    }
                
                fr.close();
                br.close();
            }
            i++;
        }
        tes.addAll(st);
        st.clear();
        st.addAll(tes);
        int j = 0;
        int pnt = -70;
        while(j<st.size()){
            
            Label l = new Label (st.get(j).toString());
            l.setTranslateX(-0);
            l.setTranslateY(pnt);
            l.setStyle("-fx-font-weight: bold");
            l.setTextFill(Color.web("#ff0000"));
            root.getChildren().add(l);
            pnt = pnt+20;
            j++;
        }
    }
    
    public void creation_index(File mot_vide,File repertoire,File r_sortie,TextArea tr1,TextArea tr2) throws IOException{
        
    int i = 0;
    for (File fichier : repertoire.listFiles()) {
        normalisation(fichier);
        tokenisation();
        File sortie = new File("/home/ilyes/Riw/Sortie/"+fichier.getName());
        elemination(mot_vide,sortie);
        i++;
    }
    File fsr = new File("/home/ilyes/Riw/Sortie");
    index_brute(fsr,tr1);
    liste_postage(fsr,tr2);
    bgram();
    fill();
    int l = 0;
    while(l< cr.size()){
        ss.add(" "+cr.get(l)+" ");
        l++;
    }
    System.out.print("Le traitement est terminé. \nMerci!");
    }
}