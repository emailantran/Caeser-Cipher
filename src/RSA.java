import java.util.*;

public class RSA {
	public static void main(String[] args) {
        int p;
        int q;
        int e;
        int N;
        int phi;
        int d;
        Scanner scanner = new Scanner(System.in);

        // get p, q, and e from user
        System.out.print("p:");
        p = scanner.nextInt();
        System.out.print("q:");
        q = scanner.nextInt();
        System.out.print("e:");
        e = scanner.nextInt();

        // calculate N
        N = q * p;
        
        //calculate phi
        phi = (p-1) * (q-1);

        // calculating d
        // d = e^-1 mod phi(N)
        // e*d congruent to 1 mod(phi)
        // soon as d is not equal, then d is correct
        d = 1;
        while((e * d) % phi != (1 % phi)){
            d++;
        }

        System.out.println(d);
    }
}
