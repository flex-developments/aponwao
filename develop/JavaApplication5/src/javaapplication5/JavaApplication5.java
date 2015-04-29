/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication5;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author flopez
 */
public class JavaApplication5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Date sigDate = new Date();
        String a = DateFormat.getDateInstance().format(sigDate) + " " + DateFormat.getTimeInstance(DateFormat.SHORT).format(sigDate);
        System.out.println(a);
    }
    
}
