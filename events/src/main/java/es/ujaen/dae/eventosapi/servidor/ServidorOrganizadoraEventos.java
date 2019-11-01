package es.ujaen.dae.eventosapi.servidor;

import java.io.IOException;



public class ServidorOrganizadoraEventos {

    public static void main(String[] args) throws IOException {
        SpringApplication servidor = new SpringApplication(ServidorOrganizadoraEventos.class);
        ApplicationContext ctx = servidor.run(args);
    }
}
