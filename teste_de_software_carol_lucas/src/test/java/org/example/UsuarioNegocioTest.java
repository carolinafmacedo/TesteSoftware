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
    // TC001: Cadastrar usuário com sucesso
    public void testAdicionarUsuarioComSucesso() {
        Usuario usuarioDoTC001 = new Usuario("usuario", "usuario@gmail.com", "123456@f");

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
        Usuario usuarioComEmailVazio = new Usuario("usuario", "", "123456@f");
        Usuario resultado = usuarioNegocio.adicionar(usuarioComEmailVazio);
        assertNull(resultado, "O resultado deveria ser nulo, pois o campo e-mail está vazio.");
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }

    @Test
    // TC003: Tentar cadastrar com login (nome) contendo espaço
    public void testAdicionarUsuarioComCadastroComEspaco_TC003() {
        Usuario usuarioComEspaco = new Usuario("usuario com espaço", "usuario@gmail.com", "123456f@");
        Usuario resultado = usuarioNegocio.adicionar(usuarioComEspaco);
        assertNull(resultado, "O sistema deveria rejeitar um login com espaços.");
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }

    @Test
    // TC004: Tentar cadastrar com e-mail em formato inválido
    public void testAdicionarUsuarioComEmailInvalido_TC004() {
        Usuario usuarioComEmailInvalido = new Usuario("usuario", "usuariogmail.com", "123456@f");
        Usuario resultado = usuarioNegocio.adicionar(usuarioComEmailInvalido);
        assertNull(resultado, "O sistema deveria rejeitar um e-mail com formato inválido.");
        verify(usuarioRepository, never()).inserir(any(Usuario.class));
    }

    @Test
    // TC_005: Realizar login de usuário com informações corretas
    public void testRealizarLoginComInformacoesCorretas_TC005() {
        String login = "usuario";
        String senha = "123456@f";
        Usuario usuarioExistente = new Usuario(login, "usuario@gmail.com", senha);

        when(usuarioRepository.findByLogin(login)).thenReturn(usuarioExistente);

        Usuario usuarioLogado = usuarioNegocio.logar(login, senha);

        assertNotNull(usuarioLogado, "O login deveria ter sucesso e retornar um usuário.");
        assertEquals(login, usuarioLogado.getNome(), "O usuário logado não corresponde ao esperado.");
    }

    @Test
    // TC_006: Realizar login de usuário com senha incorreta
    public void testRealizarLoginComSenhaIncorreta_TC006() {
        String loginCorreto = "usuario";
        String senhaCorretaNoBanco = "123456@f";
        String senhaIncorretaDigitada = "123";
        Usuario usuarioExistente = new Usuario(loginCorreto, "usuario@gmail.com", senhaCorretaNoBanco);

        when(usuarioRepository.findByLogin(loginCorreto)).thenReturn(usuarioExistente);

        Usuario resultadoLogin = usuarioNegocio.logar(loginCorreto, senhaIncorretaDigitada);

        assertNull(resultadoLogin, "O login deveria falhar e retornar nulo ao usar uma senha incorreta.");
    }

    @Test
    // TC_007: Realizar login de usuário com usuário inexistente
    public void testRealizarLoginComUsuarioInexistente_TC007() {
        String loginInexistente = "usuario_nao_existe";
        String senhaDigitada = "123456@f";

        when(usuarioRepository.findByLogin(loginInexistente)).thenReturn(null);

        Usuario resultadoLogin = usuarioNegocio.logar(loginInexistente, senhaDigitada);

        assertNull(resultadoLogin, "O login deveria falhar e retornar nulo ao usar um usuário inexistente.");
    }

    @Test
    // TC_008: Atualizar informações do perfil do usuário
    public void testAtualizarUsuarioComDadosValidos_TC008() {
        Usuario usuarioComDadosAtualizados = new Usuario("usuario", "meunome@gmail.com", "2345@ab");
        when(usuarioRepository.atualizar(usuarioComDadosAtualizados)).thenReturn(usuarioComDadosAtualizados);
        Usuario resultadoAtualizacao = usuarioNegocio.atualizar(usuarioComDadosAtualizados);
        assertNotNull(resultadoAtualizacao, "A atualização deveria ter sucesso e retornar um usuário.");
        assertEquals("meunome@gmail.com", resultadoAtualizacao.getEmail());
        assertEquals("2345@ab", resultadoAtualizacao.getSenha());
        verify(usuarioRepository, times(1)).atualizar(usuarioComDadosAtualizados);
    }

    @Test
    // TC_009: Tentar atualizar usuário com e-mail inválido
    public void testAtualizarUsuarioComEmailInvalido_TC009() {
        Usuario usuarioComEmailInvalido = new Usuario("usuario", "usuariogmail.com", "123456@f");
        Usuario resultadoAtualizacao = usuarioNegocio.atualizar(usuarioComEmailInvalido);
        assertNull(resultadoAtualizacao, "A atualização deveria falhar com e-mail inválido.");
        verify(usuarioRepository, never()).atualizar(any(Usuario.class));
    }

    @Test
    // TC_010: Tentar atualizar usuário com login vazio
    public void testAtualizarUsuarioComLoginVazio_TC010() {
        Usuario usuarioComLoginVazio = new Usuario("", "usuario@gmail.com", "123456@f");
        Usuario resultadoAtualizacao = usuarioNegocio.atualizar(usuarioComLoginVazio);
        assertNull(resultadoAtualizacao, "A atualização deveria falhar com login vazio.");
        verify(usuarioRepository, never()).atualizar(any(Usuario.class));
    }

    @Test
    // TC_011: Tentar atualizar usuário com senha em branco
    public void testAtualizarUsuarioComSenhaVazia_TC011() {
        Usuario usuarioComSenhaVazia = new Usuario("usuario", "usuario@gmail.com", "");
        Usuario resultadoAtualizacao = usuarioNegocio.atualizar(usuarioComSenhaVazia);
        assertNull(resultadoAtualizacao, "A atualização deveria falhar com senha vazia.");
        verify(usuarioRepository, never()).atualizar(any(Usuario.class));
    }

    @Test
    // TC_0018: Realizar recuperação de senha com dados válidos
    public void testRecuperarSenhaComDadosValidos_TC0018() {
        String emailExistente = "usuario@gmail.com";
        String novaSenha = "56789@f";
        Usuario usuarioExistente = new Usuario("usuario", emailExistente, "123456@f");

        when(usuarioRepository.buscarPorEmail(emailExistente)).thenReturn(usuarioExistente);
        when(usuarioRepository.atualizar(any(Usuario.class))).thenReturn(usuarioExistente);

        Usuario resultado = usuarioNegocio.recuperarSenha(emailExistente, novaSenha);

        assertNotNull(resultado, "A recuperação de senha deveria ter sucesso.");
        assertEquals(novaSenha, resultado.getSenha(), "A senha não foi atualizada corretamente.");
        verify(usuarioRepository, times(1)).buscarPorEmail(emailExistente);
        verify(usuarioRepository, times(1)).atualizar(any(Usuario.class));
    }

    @Test
    // TC_0019: Tentar recuperar senha com e-mail não cadastrado
    public void testRecuperarSenhaComEmailNaoCadastrado_TC0019() {
        String emailNaoCadastrado = "naocadastrado@gmail.com";
        when(usuarioRepository.buscarPorEmail(emailNaoCadastrado)).thenReturn(null);
        Usuario resultado = usuarioNegocio.recuperarSenha(emailNaoCadastrado, "senhaqualquer");
        assertNull(resultado, "A recuperação deveria falhar para um e-mail não cadastrado.");
        verify(usuarioRepository, never()).atualizar(any(Usuario.class));
    }

    @Test
    // TC_0020: Tentar recuperar senha com nova senha em branco
    public void testRecuperarSenhaComNovaSenhaEmBranco_TC0020() {
        String emailExistente = "usuario@gmail.com";
        String novaSenhaEmBranco = "";
        Usuario resultado = usuarioNegocio.recuperarSenha(emailExistente, novaSenhaEmBranco);
        assertNull(resultado, "A recuperação deveria falhar com senha em branco.");
        verify(usuarioRepository, never()).buscarPorEmail(anyString());
        verify(usuarioRepository, never()).atualizar(any(Usuario.class));
    }
}
