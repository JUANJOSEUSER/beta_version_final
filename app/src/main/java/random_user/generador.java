package random_user;

import java.security.SecureRandom;

public class generador {
    public  String generador_password(int len) {

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
public String generador_usuario(){
    String usuario="user";
    int ma=(int)(Math.random()*10000);
    usuario=usuario+String.valueOf(ma);
    return usuario;
}
}
