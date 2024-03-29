package es.ujaen.dae.eventosconsolamail.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;

import es.ujaen.dae.eventosconsolamail.bean.OrganizadoraEventosImp;
import es.ujaen.dae.eventosconsolamail.dao.EventoDAO;
import es.ujaen.dae.eventosconsolamail.dto.EventoDTO;
import es.ujaen.dae.eventosconsolamail.dto.UsuarioDTO;
import es.ujaen.dae.eventosconsolamail.exception.CamposVaciosException;
import es.ujaen.dae.eventosconsolamail.exception.CancelacionInvalidaException;
import es.ujaen.dae.eventosconsolamail.exception.FechaInvalidaException;
import es.ujaen.dae.eventosconsolamail.exception.InscripcionInvalidaException;
import es.ujaen.dae.eventosconsolamail.exception.SesionNoIniciadaException;
import es.ujaen.dae.eventosconsolamail.exception.UsuarioNoRegistradoNoEncontradoException;
import es.ujaen.dae.eventosconsolamail.modelo.Usuario.UserType;
import es.ujaen.dae.eventosconsolamail.servicio.OrganizadoraEventosService;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;

public class ClienteOrganizadoraEventos {

    ApplicationContext ctx;

    public ClienteOrganizadoraEventos(ApplicationContext context) {
        this.ctx = context;
    }

    public void run() throws IOException {
        OrganizadoraEventosService organizadoraEventos = (OrganizadoraEventosService) ctx
                .getBean("organizadoraEventosImp");

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        Interfaz interfaz = new Interfaz();
        int opcion;
        int flag = 0;
        boolean sesionIniciada = false;
        long token = 0;

        System.out.println("**Organizadora de eventos**");
        do {
            interfaz.imprimirOpciones("menu-principal");
            System.out.print("Ingrese el número de su elección: ");
            opcion = Integer.parseInt(bf.readLine());

            if (opcion == 1) {// Registrarse
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                System.out.println("\nComplete los siguientes campos");
                System.out.print("Ingrese su DNI: ");
                usuarioDTO.setDni(bf.readLine());
                System.out.print("Ingrese su nombre completo: ");
                usuarioDTO.setNombre(bf.readLine());
                System.out.print("Ingrese su correo electrónico: ");
                usuarioDTO.setCorreo(bf.readLine());
                System.out.print("Ingrese su número de telefono: ");
                usuarioDTO.setTelefono(bf.readLine());
                System.out.print("Ingrese su contraseña: ");
                try {
                    organizadoraEventos.registrarUsuario(usuarioDTO, bf.readLine());
                    System.out.println("Operación exitosa");
                } catch (CamposVaciosException e) {
                    System.out.println("Faltan campos por llenar");
                }
            } else if (opcion == 2) {// Iniciar sesión
                if (sesionIniciada) {
                    System.out.println("\nDebe cerrar su actual sesión primero");
                } else {
                    System.out.print("Ingrese DNI: ");
                    String dni = bf.readLine();
                    System.out.print("Ingrese password: ");
                    String respuesta = "";
                    try {
                        respuesta = organizadoraEventos.identificarUsuario(dni, bf.readLine()) + "";
                        System.out.println("Operación exitosa \nSe inició sesión correctamente");
                        sesionIniciada = true;
                        token = Long.parseLong(respuesta);
                    } catch (CamposVaciosException e) {
                        System.out.println("Faltan campos por llenar");
                    } catch (UsuarioNoRegistradoNoEncontradoException e) {
                        System.out.println("Usuario no registrado o contraseña incorrecta");
                    }
                }
            } else if (opcion == 3) {// Eventos
                do {
                    flag = 1;
                    interfaz.imprimirOpciones("menu-eventos");
                    System.out.print("Ingrese el número de su elección: ");
                    opcion = Integer.parseInt(bf.readLine());

                    if (opcion == 1) {// Buscar evento
                        System.out.print("Ingrese tipo de evento o palabra clave de la descripción: ");
                        String busqueda = bf.readLine();
                        List<EventoDTO> eventosBuscados = organizadoraEventos.buscarEvento(busqueda);

                        System.out.println("\nResultados de la búsqueda");
                        for (EventoDTO eventoDTO : eventosBuscados) {
                            System.out.println("ID: " + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                        }

                        System.out.print(
                                "¿Desea inscribirse a algún evento de la lista? Si es así, introduzca el ID del evento, "
                                + "si no, introduzca 0 para regresar al menú anterior: ");
//						System.out.println("Temporal. Cancelar evento. ID de evento: ");
                        int inscribirse = Integer.parseInt(bf.readLine());

                        if (inscribirse != 0) {// Inscribirse a evento
                            EventoDTO eventoInscribir = new EventoDTO();
                            eventoInscribir.setId(inscribirse);

                            try {
                                organizadoraEventos.inscribirEvento(eventoInscribir, token);
                                System.out.println("Operación exitosa");
                            } catch (SesionNoIniciadaException e) {
                                System.out.println("Debes iniciar sesion para inscribirte");
                            } catch (InscripcionInvalidaException e) {
                                System.out.println(
                                        "Ya perteneces a la lista de invitados, no puedes inscribirte dos veces");
                            } catch (FechaInvalidaException e) {
                                System.out.println("Este evento ya ha sido celebrado");
                            }
                        }

                        // PRUEBA CANCELAR EVENTO (ELIMINAR)
//						if (inscribirse!=0) {
//							EventoDTO eventoInscribir = new EventoDTO();
//							eventoInscribir.setId(inscribirse);
//							
//							try {
//								organizadoraEventos.cancelarEvento(eventoInscribir, token);
//								System.out.println("Operación exitosa");
//							} catch (SesionNoIniciadaException e) {
//								System.out.println("Debes iniciar sesion para cancelar evento");
//							} 
//						}
                    } else if (opcion == 2) {// Crear evento
                        EventoDTO eventoDTO = new EventoDTO();
                        // ID, nombre, descripcion, fecha, lugar, tipo y cupo son campos obligatorios

                        System.out.println("\nComplete los siguientes campos");
                        System.out.print("Ingrese el nombre del evento: ");
                        eventoDTO.setNombre(bf.readLine());
                        System.out.print("Ingrese la descripción del evento: ");
                        eventoDTO.setDescripcion(bf.readLine());
                        System.out.print("Ingrese la fecha de celebración del evento (dd-mm-aaaa): ");
                        eventoDTO.setFecha(bf.readLine());
                        System.out.print("Ingrese el lugar donde se llevará a cabo el evento: ");
                        eventoDTO.setLugar(bf.readLine());
                        System.out.print("Ingrese el tipo de evento: ");
                        eventoDTO.setTipo(bf.readLine());
                        System.out.print("Ingrese el cupo del evento: ");
                        eventoDTO.setCupo(Integer.parseInt(bf.readLine()));

                        try {
                            organizadoraEventos.crearEvento(eventoDTO, token);
                            System.out.println("Operación exitosa");
                        } catch (CamposVaciosException e) {
                            System.out.println("\nCampos vacíos");
                        } catch (SesionNoIniciadaException e) {
                            System.out.println("\nDebes iniciar sesion");
                        } catch (FechaInvalidaException e) {
                            System.out.println("\nFormato de fecha invalida");
                        }
                    } else if (opcion == 3) {// Listar eventos organizados
                        interfaz.imprimirOpciones("menu-listas");
                        System.out.print("Ingrese el número de su elección: ");
                        opcion = Integer.parseInt(bf.readLine());

                        if (opcion == 1) {// Por celebrar
                            List<EventoDTO> eventosBuscados = new ArrayList<>();

                            eventosBuscados = organizadoraEventos.listarEventoOrganizadoPorCelebrar(token);
                            System.out.println("\nEventos organizados por celebrar");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }

                            System.out.print(
                                    "¿Desea cancelar algún evento de la lista? Si es así, introduzca el ID del evento, "
                                    + "si no, introduzca 0 para regresar al menú anterior: ");
                            int cancelar = Integer.parseInt(bf.readLine());

                            if (cancelar != 0) {// Cancelar evento

                                EventoDTO eventoCancelar = new EventoDTO();
                                eventoCancelar.setId(cancelar);

                                try {
                                    organizadoraEventos.cancelarEvento(eventoCancelar, token);
                                    System.out.println("Operación exitosa");
                                } catch (CancelacionInvalidaException e) {
                                    System.out.println("No tienes permiso de cancelar este evento");
                                } catch (SesionNoIniciadaException e) {
                                    System.out.println("Debes iniciar sesion");
                                }
                            }
                        } else if (opcion == 2) {// Celebrado
                            List<EventoDTO> eventosBuscados = new ArrayList<>();

                            eventosBuscados = organizadoraEventos.listarEventoOrganizadoCelebrado(token);
                            System.out.println("\nEventos organizados celebrado");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }
                        }
                    } else if (opcion == 4) {// Listar eventos inscritos
                        interfaz.imprimirOpciones("menu-listas");
                        System.out.print("Ingrese el número de su elección: ");
                        opcion = Integer.parseInt(bf.readLine());

                        if (opcion == 1) {// Por celebrar
                            List<EventoDTO> eventosBuscados = new ArrayList<>();
                            eventosBuscados = organizadoraEventos.listarEventoInscritoPorCelebrar(token);
                            System.out.println("\nEventos inscrito por celebrar");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }

                            System.out.print(
                                    "¿Desea cancelar la inscripción a un evento de la lista? Si es así, introduzca el ID del evento, "
                                    + "si no, introduzca 0 para regresar al menú anterior: ");
                            int cancelar = Integer.parseInt(bf.readLine());

                            if (cancelar != 0) {// Cancelar inscripcion

                                EventoDTO eventoCancelar = new EventoDTO();
                                eventoCancelar.setId(cancelar);

                                try {
                                    organizadoraEventos.cancelarInscripcion(eventoCancelar, token);
                                    System.out.println("Operación exitosa");
                                } catch (CancelacionInvalidaException e) {
                                    System.out.println("No puedes cancelar un evento que ya se celebró");
                                } catch (SesionNoIniciadaException e) {
                                    System.out.println("Debes iniciar sesion");
                                }
                            }
                        } else if (opcion == 2) {// Celebrado
                            List<EventoDTO> eventosBuscados = new ArrayList<>();
                            eventosBuscados = organizadoraEventos.listarEventoInscritoCelebrado(token);
                            System.out.println("\nEventos inscritos celebrado");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }
                        }
                    } else if (opcion == 5) {// Listar eventos en espera
                        interfaz.imprimirOpciones("menu-listas");
                        System.out.print("Ingrese el número de su elección: ");
                        opcion = Integer.parseInt(bf.readLine());

                        if (opcion == 1) {// Por celebrar
                            List<EventoDTO> eventosBuscados = new ArrayList<>();

                            eventosBuscados = organizadoraEventos.listarEventoEsperaPorCelebrar(token);
                            System.out.println("\nEventos en espera por celebrar");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }

                            System.out.print(
                                    "¿Desea cancelar la inscripción de algún evento de la lista? Si es así, introduzca el ID del evento, "
                                    + "si no, introduzca 0 para regresar al menú anterior: ");
                            int cancelar = Integer.parseInt(bf.readLine());

                            if (cancelar != 0) {// Cancelar lista de espera

                                EventoDTO eventoCancelar = new EventoDTO();
                                eventoCancelar.setId(cancelar);
                                try {
                                    organizadoraEventos.cancelarListaEspera(eventoCancelar, token);
                                    System.out.println("Operación exitosa");
                                } catch (CancelacionInvalidaException e) {
                                    System.out.println("No puedes cancelar un evento que ya se celebró");
                                }
//								} catch (SesionNoIniciadaException e) {
//									System.out.println("Debes iniciar sesion");
//								}
                            }
                        } else if (opcion == 2) {// Celebrado
                            List<EventoDTO> eventosBuscados = new ArrayList<>();

                            eventosBuscados = organizadoraEventos.listarEventoEsperaCelebrado(token);
                            System.out.println("\nEventos en espera celebrado");
                            for (EventoDTO eventoDTO : eventosBuscados) {
                                System.out.println("ID:" + eventoDTO.getId() + "  " + eventoDTO.getNombre());
                            }
                        }
                    }  else if (opcion == 6) {// usuario tipo
                    	 if (sesionIniciada) {
                             System.out.println("\nDebe cerrar su actual sesión primero");
                         } else {
                             System.out.print("Ingrese DNI: ");
                             String dni = bf.readLine();
                             System.out.print("Ingrese tipo de usuario  de creacion de eventos requerida: ");
                             UserType respuesta = UserType.valueOf(bf.readLine());
                             try {
                                  organizadoraEventos.changeType(dni, respuesta);
                                 System.out.println("Operación exitosa \nSe inició sesión correctamente");
                                 sesionIniciada = true;
                             } catch (UsuarioNoRegistradoNoEncontradoException e) {
                                 System.out.println("Usuario no registrado o contraseña incorrecta");
                             }
                         }
                    }
                    else if (opcion == 7) {// Ir a menú principal
                        flag = 0;
                    }

                } while (flag == 1);
            } else if (opcion == 4) {// Cerrar sesión

                if (organizadoraEventos.cerrarSesion(token)) {
                    System.out.println("\n " + "Sesión terminada. Hasta luego");
                    sesionIniciada = false;
                } else {
                    System.out.println("\n " + "Token incorrecto");
                }

            } else if (opcion == 5) {// Salir
                System.out.println("Vuelva pronto");
                flag = 2;
            }

        } while (flag == 0);

        bf.close();

    }

}
