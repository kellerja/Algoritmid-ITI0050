package ee.ttu.algoritmid.crypto;

import java.math.BigInteger;

public class AL08 {
    private Session session;

    /**
     * Constructor.
     * See https://en.wikipedia.org/wiki/Diffie%E2%80%93Hellman_key_exchange for information.
     *
     * @param session the session, that contains information about the key exchange
     */
    public AL08(Session session) {
        this.session = session;
    }

    /**
     * Crack Alice's private key (a) using the available information from the session.
     * See the public methods in the Session class to get the necessary data.
     *
     * @return the value of a (the secret integer of Alice)
     */
    public Integer crackAlice() {
        int p = session.getP();
        int g = session.getG();
        try {
            int A = 1;
            for (int a = 1; a < p; a++) {
                A *= g;
                A %= p;
                if (session.getAlicesPublicKey() == A) {
                    return a;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Crack Bob's private key (b) using the available information from the session.
     * See the public methods in the Session class to get the necessary data.
     *
     * @return the value of b (the secret integer of Bob)
     */
    public Integer crackBob() {
        int p = session.getP();
        int A = session.getAlicesPublicKey();
        int a = crackAlice();
        int g = BigInteger.valueOf(session.getG()).pow(a).mod(BigInteger.valueOf(p)).intValue();
        for (int b = 1; b < p; b++) {
            A *= A;
            A %= p;
            g *= g;
            g %= p;
            System.out.println(a + " " + A + " " + p + " " + g + " " + b);
            if (A == g) return b;
        }
        return null;
    }

    public static void main(String[] args) {
        Session session = new Session(4, 3, 23, 5);
        AL08 al08 = new AL08(session);
        System.out.println(al08.crackAlice());
        System.out.println(al08.crackBob());
    }
}