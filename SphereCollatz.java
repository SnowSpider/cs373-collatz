import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.io.PrintWriter;

class Main {
    private static final int SIZE = 1000000;
    private static int[] cache_eval = new int[SIZE];
    private static int[] cache_cycLen = new int[SIZE];
    
    public static void main (String[] args) throws IOException {
        final Scanner r = new Scanner(System.in);
        final Writer w = new PrintWriter(System.out);
        Main.solve(r, w);
    }

    public static boolean read (Scanner r, int[] a) {
        if (!r.hasNextInt())
            return false;
        a[0] = r.nextInt();
        a[1] = r.nextInt();
        assert a[0] > 0;
        assert a[1] > 0;
        return true;}

    public static int eval (int i, int j) {
        assert i > 0;
        assert j > 0;
        int v = 1;
        assert i < SIZE;
        assert j < SIZE; 
        int temp = 0;
        if(i > j){ 
            temp = j;
            j = i;
            i = temp;
        }
        assert i <= j;
        
        temp = 0;
        int di = i<<1; 
        if(di < j){
            for(int k=j; k>=di; k--){
                if(cache_eval[k] > 0){
                    temp = k;
                    break;
                }
            }
        }
        
        if(temp > 0){
            int a = eval(i, temp>>1); 
            int b = cache_eval[temp]; 
            int c = eval(temp, j); 
            v = ((a > b) ? a : b);
            v = ((v > c) ? v : c);
        }
        else{
            if(i < (j>>1)){
                if(cache_eval[j] > 0){
                    return cache_eval[j];
                }
                i = j>>1; 
            }
            for(int k = i; k <= j; k++){
                temp = getCycLen(k);
                if(temp > v){
                    v = temp; 
                }
            } 
        }
        
        assert v > 0;
        cache_eval[j] = v;
        return v;
    }
    
    public static int getCycLen (int n){ 
        assert n > 0;
        if(n == 1){ 
            return 1; 
        }
        else if(n % 2 == 1){ 
            if(n < SIZE){
                if(cache_cycLen[n] > 0) return cache_cycLen[n]; 
                cache_cycLen[n] = 2 + getCycLen( n + (n>>1) + 1 );
                return cache_cycLen[n];
            }
            else return 2 + getCycLen( n + (n>>1) + 1 ); 
        }
        else{
            if(n < SIZE){
                if(cache_cycLen[n] > 0) return cache_cycLen[n]; 
                cache_cycLen[n] = 1 + getCycLen( n>>1 );
                return cache_cycLen[n];
            }
            return 1 + getCycLen( n>>1 );
        }
    }

    public static void print (Writer w, int i, int j, int v) throws IOException {
        assert i > 0;
        assert j > 0;
        assert v > 0;
        w.write(i + " " + j + " " + v + "\n");
        w.flush();}

    public static void solve (Scanner r, Writer w) throws IOException {
        final int[] a = {0, 0};
        while (Main.read(r, a)) {
            final int v = Main.eval(a[0], a[1]);
            Main.print(w, a[0], a[1], v);
        }
    }
}



