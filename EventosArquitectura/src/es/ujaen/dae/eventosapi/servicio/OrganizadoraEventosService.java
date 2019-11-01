package es.ujaen.dae.eventosapi.servicio;

import java.util.List;

import es.ujaen.dae.eventosapi.dto.EventoDTO;
import es.ujaen.dae.eventosapi.dto.UsuarioDTO;
import es.ujaen.dae.eventosapi.exception.CamposVaciosException;
import es.ujaen.dae.eventosapi.exception.CancelacionInvalidaException;
import es.ujaen.dae.eventosapi.exception.FechaInvalidaException;
import es.ujaen.dae.eventosapi.exception.InscripcionInvalidaException;
import es.ujaen.dae.eventosapi.exception.UsuarioNoRegistradoNoEncontradoException;

public interface OrganizadoraEventosService {

    // mostrar usuarios
    // public void obtenerUsuarios();
    // public void obtenerEventos();
    /////////////////////////
    public void registrarUsuario(UsuarioDTO usuarioDTO)throws CamposVaciosException;// Probado
    
    public UsuarioDTO obtenerUsuario(String dni)throws CamposVaciosException, UsuarioNoRegistradoNoEncontradoException;

    public void crearEvento(EventoDTO eventoDTO) throws CamposVaciosException;// Probado

    public void inscribirEvento(int id, String dni)
            throws InscripcionInvalidaException, FechaInvalidaException;// Probado

    public void cancelarInscripcion(int id, String dni)
            throws CancelacionInvalidaException, UsuarioNoRegistradoNoEncontradoException;// Probado

    public List<EventoDTO> buscarEventoPorTipo(String attr);// Probado
    
    public List<EventoDTO> buscarEventoPorDescripcion(String attr);// Probado    
    public EventoDTO buscarEventoPorId(int id);// Probado

    public void cancelarEvento(int id, String dni)
            throws CancelacionInvalidaException;// Probado

    public List<EventoDTO> listarEventoInscritoCelebrado(String dni);// Probado

    public List<EventoDTO> listarEventoInscritoPorCelebrar(String dni);// Probado

    public List<EventoDTO> listarEventoEsperaPorCelebrar(String dni); // Probado

    public List<EventoDTO> listarEventoEsperaCelebrado(String dni); // Probado

    public List<EventoDTO> listarEventoOrganizadoCelebrado(String dni);// Probado

    public List<EventoDTO> listarEventoOrganizadoPorCelebrar(String dni);// Probado

    public void cancelarListaEspera(EventoDTO eventoDTO) throws CancelacionInvalidaException;

}
