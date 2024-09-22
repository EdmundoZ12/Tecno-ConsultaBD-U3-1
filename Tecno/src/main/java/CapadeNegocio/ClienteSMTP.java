/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapadeNegocio;

import java.io.*;
import java.net.*;

public class ClienteSMTP {

    public static void main(String[] args) {
        String servidor = "mail.ficct.uagrm.edu.bo";
        String user_receptor = "grupo01sa@ficct.uagrm.edu.bo";
        String user_emisor = "evansbv@gmail.com";
        String comando = "";
        int puerto = 25;

        try {
            // Se establece conexión abriendo un socket especificando el servidor y puerto SMTP
            Socket socket = new Socket(servidor, puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

            // Escribimos datos en el canal de salida establecido con el puerto del protocolo SMTP del servidor
            if (socket != null && entrada != null && salida != null) {
                System.out.println("S : " + entrada.readLine());

                comando = "EHLO " + servidor + "\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + getMultiline(entrada));

                comando = "MAIL FROM:<" + user_emisor + ">\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());

                comando = "RCPT TO:<" + user_receptor + ">\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());

                comando = "DATA\r\n"; // Cambiado a "DATA\r\n"
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + getMultiline(entrada));

                String mensaje = "Subject: DEMO X\r\n"
                        + "From: " + user_emisor + "\r\n"
                        + "To: " + user_receptor + "\r\n"
                        + "\r\n"
                        + // Línea vacía para separar encabezados del cuerpo
                        "Probando\n"
                        + "el envio de mensajes\n"
                        + ".\r\n";  // Finaliza el mensaje con un punto en una nueva línea
                System.out.print("C : " + mensaje);
                salida.writeBytes(mensaje);
                System.out.println("S : " + entrada.readLine());

                comando = "QUIT\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());
            }

            // Cerramos los flujos de salida y de entrada y el socket cliente
            salida.close();
            entrada.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("S : No se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Permite leer múltiples líneas del Protocolo SMTP
    static protected String getMultiline(BufferedReader in) throws IOException {
        String lines = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                // Server closed connection
                throw new IOException("S : Server unawares closed the connection.");
            }
            if (line.charAt(3) == ' ') {
                lines += "\n" + line;
                // No more lines in the server response
                break;
            }
            // Add read line to the list of lines
            lines += "\n" + line;
        }
        return lines;
    }
}
