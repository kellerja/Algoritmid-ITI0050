package ee.ttu.algoritmid.crypto;

import javax.crypto.BadPaddingException;
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
        String m = "qwerty";
        try {
            String c = session.getEncrypted(m);
            int s = 1;
            for (int b = 1; b < p; b++) {
                s *= A;
                s %= p;
                if (session.getEncryptedWithCustomKey(m, s).equals(c)) return b;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Session session = new Session(6, 15, 5, 23);
        AL08 al08 = new AL08(session);
        System.out.println(al08.crackAlice());
        System.out.println(al08.crackBob());
    }
}