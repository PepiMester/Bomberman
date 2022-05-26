package Bomberman;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class SynchronizedInputStream  extends InputStream {

    private InputStream in;

    public SynchronizedInputStream(InputStream in) {
        this.in = in;
    }

    /* ... method for every InputStream type to use */
    public  static InputStream createInputStream( InputStream in) {
        return new SynchronizedInputStream( in);
    }

    public static InputStream createPushBackInputStream(InputStream in,int BUFSIZE){
        return new SynchronizedInputStream(new PushbackInputStream(in,BUFSIZE));
    }

    /* Wrap all InputStream methods Used */

    public int read(){
        synchronized (this) {
            try {
               return in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int available() {
        synchronized( this ) {
            try {
                return in.available();
            } catch (IOException t) {
                t.printStackTrace();
            }
        }
        return 0;
    }


   
}

