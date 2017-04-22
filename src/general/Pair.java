/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

/**
 *
 * @author bendy
 * @param <A>
 * @param <B>
 */
public class Pair<A,B> {
    A key;
    B val;
    
    public Pair(A key, B value){
        this.key = key;
        val = value;
    }
}
