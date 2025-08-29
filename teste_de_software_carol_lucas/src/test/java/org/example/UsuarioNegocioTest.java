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
// TC_002: Tentar cadastrar usuário com e-mail vazio
    public void testAdicionarUsuarioComEmailVazio_TC002() {
        // Cenário: usuário com login e senha preenchidos, mas sem e-mail
        Usuario usuarioComEmailVazio = new Usuario(
                "usuario",
                "", // e-mail vazio
                "123456@f"
        );

        // Ação: tentativa de cadastro
        Usuario resultado = usuarioNegocio.adicionar(usuarioComEmailVazio);

        // Resultado esperado: cadastro deve falhar, retornando null
        assertNull(resultado,
                "O resultado deveria ser nulo, pois o campo e-mail está vazio e o sistema não deve permitir o cadastro.");

        // Garantir que o repositório não foi chamado
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }


    @Test
// TC003: Tentar cadastrar com login (nome) contendo espaço
    public void testAdicionarUsuarioComCadastroComEspaco_TC003() {
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

    @Test
// TC_005: Realizar login de usuário com informações corretas
    public void testRealizarLoginComInformacoesCorretas_TC005() {
        // Arrange
        String login = "usuario";
        String senha = "123456@f";
        Usuario usuarioExistente = new Usuario(login, "usuario@gmail.com", senha);

        when(usuarioRepository.findByLogin(login)).thenReturn(usuarioExistente);

        // Act
        Usuario usuarioLogado = usuarioNegocio.logar(login, senha);

        // Assert
        assertNotNull(usuarioLogado, "O login deveria ter sucesso e retornar um usuário.");
        assertEquals(login, usuarioLogado.getNome(), "O usuário logado não corresponde ao esperado.");
    }
    @Test
// TC_006: Realizar login de usuário com senha incorreta
    public void testRealizarLoginComSenhaIncorreta_TC006() {
        // Arrange: Prepara o cenário
        // 1. O usuário que "existe" no banco tem uma senha correta.
        String loginCorreto = "usuario";
        String senhaCorretaNoBanco = "123456@f";
        Usuario usuarioExistente = new Usuario(loginCorreto, "usuario@gmail.com", senhaCorretaNoBanco);

        // 2. A senha que o usuário vai digitar na tela (e que vamos testar) está errada.
        String senhaIncorretaDigitada = "123"; // Senha errada, como no seu plano de teste

        // 3. Simulamos que o repositório ENCONTRA o usuário pelo login correto.
        when(usuarioRepository.findByLogin(loginCorreto)).thenReturn(usuarioExistente);

        // Act: Executa a ação de logar com a senha INCORRETA
        Usuario resultadoLogin = usuarioNegocio.logar(loginCorreto, senhaIncorretaDigitada);

        // Assert: Verifica se o resultado foi o esperado (FALHA)
        // O sistema deve rejeitar o login, portanto, o resultado deve ser nulo.
        assertNull(resultadoLogin, "O login deveria falhar e retornar nulo ao usar uma senha incorreta.");
    }





}



