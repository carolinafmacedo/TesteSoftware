package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class UsuarioNegocioTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    private UsuarioNegocio usuarioNegocio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioNegocio = new UsuarioNegocio(usuarioRepository);
    }

    @Test
    // Teste TC001: Cadastrar usuário com sucesso
    public void testAdicionarUsuarioComSucesso() {
        Usuario usuarioDoTC001 = new Usuario(
                "usuario",
                "usuario@gmail.com",
                "123456@f"
        );

        when(usuarioRepository.buscarPorEmail(anyString())).thenReturn(null);
        when(usuarioRepository.inserir(usuarioDoTC001)).thenReturn(usuarioDoTC001);

        Usuario resultado = usuarioNegocio.adicionar(usuarioDoTC001);

        assertNotNull(resultado);
        assertEquals("usuario", resultado.getNome());
        assertEquals("usuario@gmail.com", resultado.getEmail());
        assertEquals("123456@f", resultado.getSenha());
        verify(usuarioRepository, times(1)).inserir(usuarioDoTC001);
    }

    @Test
// TC002: Tentar cadastrar com e-mail vazio
    public void testAdicionarUsuarioComEmailVazio_TC002() {
        // Cenário: Criamos o usuário com e-mail vazio.
        Usuario usuarioComEmailVazio = new Usuario(
                "usuario",
                "", // E-mail vazio
                "123456@f"
        );

        Usuario resultado = usuarioNegocio.adicionar(usuarioComEmailVazio);
        assertNotNull(resultado, "Esta mensagem aparecerá no erro: O resultado não deveria ser nulo!");
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }

    @Test
// TC003: Tentar cadastrar com login (nome) contendo espaço
    public void testAdicionarUsuarioComLoginComEspaco_TC003() {
        Usuario usuarioComEspaco = new Usuario(
                "usuario com espaço", // Login inválido com espaço
                "usuario@gmail.com",
                "123456f@"
        );

        Usuario resultado = usuarioNegocio.adicionar(usuarioComEspaco);
        assertNull(resultado, "O sistema deveria rejeitar um login com espaços.");
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }

    @Test
// TC004: Tentar cadastrar com e-mail em formato inválido
    public void testAdicionarUsuarioComEmailInvalido_TC004() {
        // Cenário: Criamos o usuário com os dados exatos do TC004
        Usuario usuarioComEmailInvalido = new Usuario(
                "usuario",
                "usuariogmail.com", // E-mail inválido, sem o "@"
                "123456@f"
        );
        Usuario resultado = usuarioNegocio.adicionar(usuarioComEmailInvalido);

        assertNull(resultado, "O sistema deveria rejeitar um e-mail com formato inválido.");
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }
}