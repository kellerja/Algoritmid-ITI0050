package ee.ttu.algoritmid.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.Key;
import java.util.Base64;

public class Session {
    private static final String ALGORITHM = "AES";

    private int a;
    private int b;
    private int g;
    private int p;

    /**
     * The constructor.
     *
     * @param a Alice's private key (value a)
     * @param b Bob's private key (value b)
     * @param g base value g
     * @param p modulus value p
     */
    public Session(int a, int b, int g, int p) {
        this.a = a;
        this.b = b;
        this.g = g;
        this.p = p;
    }

    /**
     * Get Alice's public key (A).
     *
     * @return the value of A (Alice's public key)
     */
    public int getAlicesPublicKey() {
        BigInteger A = BigInteger.valueOf(this.g).pow(this.a).mod(BigInteger.valueOf(this.p));
        return A.intValue();
    }

    /**
     * Get the g value.
     *
     * @return the value of g
     */
    public int getG() {
        return this.g;
    }

    /**
     * Get the p value.
     *
     * @return the value of p
     */
    public int getP() {
        return this.p;
    }

    /**
     * Encrypt any string with the correct secret key (s) that Alice and Bob came upon.
     *
     * @param msg string to be encrypted
     *
     * @return encrypted message
     */
    public String getEncrypted(String msg) throws Exception {
        BigInteger gBig = BigInteger.valueOf(this.g);
        BigInteger pBig = BigInteger.valueOf(this.p);
        BigInteger sBig = gBig.pow(this.a * this.b).mod(pBig);
        return getEncryptedWithCustomKey(msg, sBig.intValue());
    }

    /**
     * Encrypt any string with any integer key.
     *
     * @param msg string to be encrypted
     * @param intKey encryption key as an integer
     *
     * @return encrypted message
     */
    public String getEncryptedWithCustomKey(String msg, int intKey) throws Exception {
        Key key = generateKeyFromInt(intKey);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(msg.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }

    /**
     * Decrypt any string with any key.
     *
     * @param encryptedMsg encrypted message to be decrypted
     * @param intKey key used for decrypting as an integer
     *
     * @return decrypted message
     */
    public String getDecryptedWithCustomKey(String encryptedMsg, int intKey) throws Exception {
        Key key = generateKeyFromInt(intKey);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.getDecoder().decode(encryptedMsg);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    private static Key generateKeyFromInt(int key) throws Exception {
        byte[] bytes = ByteBuffer.allocate(16).putInt(key).array();
        return generateKey(bytes);
    }

    private static Key generateKey(byte[] keyValue) throws Exception {
        return new SecretKeySpec(keyValue, ALGORITHM);
    }
}
