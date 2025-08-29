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
    @Test
    // TC_007: Realizar login de usuário com usuário inexistente e senha correta
    public void testRealizarLoginComUsuarioInexistente_TC007() {
        // Arrange: Prepara o cenário
        String loginInexistente = "usuario_nao_existe";
        String senhaDigitada = "123456@f";

        // Simulamos que o repositório NÃO ENCONTRA o usuário pelo login.
        when(usuarioRepository.findByLogin(loginInexistente)).thenReturn(null);

        // Act: Executa a ação de logar com um login INEXISTENTE
        Usuario resultadoLogin = usuarioNegocio.logar(loginInexistente, senhaDigitada);

        // Assert: Verifica se o resultado foi o esperado (FALHA)
        assertNull(resultadoLogin, "O login deveria falhar e retornar nulo ao usar um usuário inexistente.");
    }

    @Test
// TC_008: Atualizar informações do perfil do usuário
    public void testAtualizarUsuarioComDadosValidos_TC008() {
        // Arrange: Prepara o cenário
        // 1. Criamos um usuário com os dados ATUAIS
        String loginExistente = "usuario";
        Usuario usuarioExistente = new Usuario(
                loginExistente,
                "usuario@gmail.com",
                "123456@f"
        );

        // 2. Criamos o mesmo usuário, mas com os dados ATUALIZADOS do plano de teste
        Usuario usuarioComDadosAtualizados = new Usuario(
                loginExistente,
                "meunome@gmail.com",
                "2345@ab"
        );

        // 3. Simulamos que a atualização no repositório será um sucesso.
        // O método 'atualizar' do repositório deve retornar o usuário atualizado.
        when(usuarioRepository.atualizar(usuarioComDadosAtualizados)).thenReturn(usuarioComDadosAtualizados);

        // Act: Executa a ação de atualizar o perfil
        // Assumimos que a classe UsuarioNegocio possui um método 'atualizar'
        Usuario resultadoAtualizacao = usuarioNegocio.atualizar(usuarioComDadosAtualizados);

        // Assert: Verifica se a atualização foi bem-sucedida e se os dados estão corretos
        // A atualização deveria ter sucesso, retornando o objeto de usuário atualizado.
        assertNotNull(resultadoAtualizacao, "A atualização deveria ter sucesso e retornar um usuário.");
        assertEquals(usuarioComDadosAtualizados.getEmail(), resultadoAtualizacao.getEmail(),
                "O e-mail do usuário não foi atualizado corretamente.");
        assertEquals(usuarioComDadosAtualizados.getSenha(), resultadoAtualizacao.getSenha(),
                "A senha do usuário não foi atualizada corretamente.");

        // Verificamos se o método 'atualizar' do repositório foi chamado UMA vez
        verify(usuarioRepository, times(1)).atualizar(usuarioComDadosAtualizados);
    }
    @Test
// TC_009: Tentar atualizar usuário com e-mail inválido
    public void testAtualizarUsuarioComEmailInvalido_TC009() {
        // Arrange: Prepara o cenário
        // Criamos um usuário com os dados de atualização, mas com um e-mail inválido.
        String loginExistente = "usuario";
        Usuario usuarioComEmailInvalido = new Usuario(
                loginExistente,
                "usuariogmail.com", // E-mail inválido, sem o "@"
                "123456@f"
        );

        // Act: Executa a ação de tentar atualizar o perfil
        Usuario resultadoAtualizacao = usuarioNegocio.atualizar(usuarioComEmailInvalido);

        // Assert: Verifica se a atualização falhou
        // O sistema deve rejeitar a atualização, retornando null.
        assertNull(resultadoAtualizacao,
                "A atualização deveria falhar e retornar nulo ao usar um e-mail inválido.");

        // Verifica se o método de atualização do repositório NUNCA foi chamado.
        // A validação deve ocorrer no UsuarioNegocio, impedindo a chamada ao banco de dados.
        verify(usuarioRepository, never()).atualizar(any(Usuario.class));
    }
    @Test
// TC_010: Tentar atualizar usuário com login vazio
    public void testAtualizarUsuarioComLoginVazio_TC010() {
        // Arrange: Prepara o cenário
        // Criamos um objeto de usuário com o campo de login vazio, como no plano de teste.
        Usuario usuarioComLoginVazio = new Usuario(
                "", // Login vazio
                "usuario@gmail.com",
                "123456@f"
        );

        // Act: Executa a ação de tentar atualizar o perfil
        Usuario resultadoAtualizacao = usuarioNegocio.atualizar(usuarioComLoginVazio);

        // Assert: Verifica se a atualização falhou
        // O sistema deve rejeitar a atualização, retornando null.
        assertNull(resultadoAtualizacao,
                "A atualização deveria falhar e retornar nulo ao usar um login vazio.");

        // Verifica se o método de atualização do repositório NUNCA foi chamado.
        // A validação deve ocorrer no UsuarioNegocio, impedindo a chamada ao banco de dados.
        verify(usuarioRepository, never()).atualizar(any(Usuario.class));
    }
    @Test
// TC_011: Tentar atualizar usuário com senha em branco
    public void testAtualizarUsuarioComSenhaVazia_TC011() {
        // Arrange: Prepara o cenário
        // Criamos um objeto de usuário com a senha em branco, como no plano de teste.
        Usuario usuarioComSenhaVazia = new Usuario(
                "usuario",
                "usuario@gmail.com",
                "" // Senha vazia
        );

        // Act: Executa a ação de tentar atualizar o perfil
        Usuario resultadoAtualizacao = usuarioNegocio.atualizar(usuarioComSenhaVazia);

        // Assert: Verifica se a atualização falhou
        // O sistema deve rejeitar a atualização, retornando null.
        assertNull(resultadoAtualizacao,
                "A atualização deveria falhar e retornar nulo ao usar uma senha vazia.");

        // Verifica se o método de atualização do repositório NUNCA foi chamado.
        // A validação deve ocorrer no UsuarioNegocio, impedindo a chamada ao banco de dados.
        verify(usuarioRepository, never()).atualizar(any(Usuario.class));
    }
}



