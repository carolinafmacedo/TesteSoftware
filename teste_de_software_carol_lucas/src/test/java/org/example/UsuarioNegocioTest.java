package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

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

    @Test
    public void testAdicionarUsuarioComTelefoneExistente() {
        when(usuarioRepository.buscarPorTelefone(anyString())).thenReturn(true);

        Usuario resultado = usuarioNegocio.adicionar(usuario);

        assertNull(resultado);
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }

    @Test
    public void testAdicionarUsuarioComEmailExistente() {
        when(usuarioRepository.buscarPorTelefone(anyString())).thenReturn(false);
        when(usuarioRepository.buscarPorEmail(anyString())).thenReturn(true);

        Usuario resultado = usuarioNegocio.adicionar(usuario);

        assertNull(resultado);
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }

    @Test
    public void testAdicionarUsuarioComNomeNulo() {
        usuario.setNome(null);

        Usuario resultado = usuarioNegocio.adicionar(usuario);

        assertNull(resultado);
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }

    @Test
    public void testAdicionarUsuarioComEnderecoVazio() {
        usuario.setEndereco(" ");

        Usuario resultado = usuarioNegocio.adicionar(usuario);

        assertNull(resultado);
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }
    
    @Test
    public void testAdicionarUsuarioComDataNascimentoNula() {
        usuario.setDataNascimento(null);

        Usuario resultado = usuarioNegocio.adicionar(usuario);

        assertNull(resultado);
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }
    
    @Test
    public void testAdicionarUsuarioComEmailNulo() {
        usuario.setEmail(null);

        Usuario resultado = usuarioNegocio.adicionar(usuario);

        assertNull(resultado);
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }

    @Test
    public void testAdicionarUsuarioComTelefoneVazio() {
        usuario.setTelefone("");

        Usuario resultado = usuarioNegocio.adicionar(usuario);

        assertNull(resultado);
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }
}