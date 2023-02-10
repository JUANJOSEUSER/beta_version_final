package random_user;

import java.security.SecureRandom;

public class generador {
    public  String generador_password(int len) {

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        int numero=0;
        String num="";
        for (int i = 0; i < (int)(Math.random()*5); i++) {
            numero=(int)(Math.random()*9);
            num+=String.valueOf(numero);
        }
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString().concat(num);
    }
public String generador_usuario(){
    String usuario="user";
    int ma=(int)(Math.random()*10000);
    usuario=usuario+String.valueOf(ma);
    return usuario;
}
}
