package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioNegocioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioNegocio usuarioNegocio;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario("Teste", "Rua Teste", "01/01/2000", "teste@email.com", "123456789", "senha123");
    }

    @Test
    public void testAdicionarUsuarioComSucesso() {
        when(usuarioRepository.buscarPorTelefone(anyString())).thenReturn(false);
        when(usuarioRepository.buscarPorEmail(anyString())).thenReturn(false);
        when(usuarioRepository.inserir(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioNegocio.adicionar(usuario);

        assertNotNull(resultado);
        assertEquals("Teste", resultado.getNome());
        verify(usuarioRepository, times(1)).inserir(usuario);
    }
}