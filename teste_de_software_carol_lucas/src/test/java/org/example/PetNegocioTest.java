package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PetNegocioTest {

    @Mock
    private PetRepository petRepository; // Mock da nossa classe concreta

    @InjectMocks
    private PetNegocio petNegocio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    // TC_012: Realizar cadastro de pet no sistema com dados válidos
    public void testCadastrarPetComDadosValidos_TC012() {
        // Arrange: Prepara o cenário
        Usuario dono = new Usuario("lucas", "lucas@gmail.com", "123");
        Pet petParaCadastrar = new Pet("Thor", "Canina", "Poodle", "Leitura QR Code", "Instituto Federal de Pernambuco");

        when(petRepository.inserir(any(Pet.class))).thenReturn(petParaCadastrar);

        // Act: Executa a ação de cadastrar o pet
        Pet petCadastrado = petNegocio.cadastrar(petParaCadastrar, dono);

        // Assert: Verifica os resultados
        assertNotNull(petCadastrado, "O cadastro deveria ter sucesso e retornar o pet.");
        assertEquals("Thor", petCadastrado.getNome());
        assertEquals(dono.getNome(), petCadastrado.getDonoLogin(), "O pet não foi associado ao dono correto.");
        verify(petRepository, times(1)).inserir(petParaCadastrar);
    }
    @Test
// TC_0013: Tentar cadastrar pet com nome em branco
    public void testCadastrarPetComNomeEmBranco_TC0013() {
        // Arrange: Prepara o cenário
        // 1. Simulamos um usuário logado que será o dono.
        Usuario dono = new Usuario("lucas", "lucas@gmail.com", "123");

        // 2. Criamos um pet com o nome em branco, como especificado no plano de teste.
        Pet petComNomeEmBranco = new Pet(
                "", // Nome em branco
                "Canina",
                "Poodle",
                "Leitura QR Code",
                "Instituto Federal de Pernambuco"
        );

        // Act: Executa a ação de tentar cadastrar o pet com dados inválidos
        Pet resultadoCadastro = petNegocio.cadastrar(petComNomeEmBranco, dono);

        // Assert: Verifica se o cadastro foi rejeitado
        // O resultado deve ser nulo, pois o nome é um campo obrigatório.
        assertNull(resultadoCadastro, "O sistema deveria retornar nulo ao tentar cadastrar um pet com nome em branco.");

        // Garante que o sistema nem tentou salvar no banco de dados,
        // pois a validação na camada de negócio deveria falhar antes.
        verify(petRepository, never()).inserir(any(Pet.class));
    }
    @Test
// TC_0014: Tentar cadastrar pet com raça em branco
    public void testCadastrarPetComRacaEmBranco_TC0014() {
        // Arrange: Prepara o cenário
        // 1. Simulamos um usuário logado.
        Usuario dono = new Usuario("lucas", "lucas@gmail.com", "123");

        // 2. Criamos um pet com a raça em branco ("").
        Pet petComRacaEmBranco = new Pet(
                "Thor",
                "Canina",
                "", // Raça em branco
                "Leitura QR Code",
                "Instituto Federal de Pernambuco"
        );

        // Act: Tenta cadastrar o pet com a raça inválida
        Pet resultadoCadastro = petNegocio.cadastrar(petComRacaEmBranco, dono);

        // Assert: Verifica se o cadastro foi corretamente rejeitado
        assertNull(resultadoCadastro, "O sistema deveria retornar nulo ao tentar cadastrar um pet com raça em branco.");

        // Garante que o repositório NUNCA foi chamado.
        verify(petRepository, never()).inserir(any(Pet.class));
    }
    @Test
// TC_0015: Tentar cadastrar pet com espécie em branco
    public void testCadastrarPetComEspecieEmBranco_TC0015() {
        // Arrange: Prepara o cenário
        // 1. Simulamos um usuário logado.
        Usuario dono = new Usuario("lucas", "lucas@gmail.com", "123");

        // 2. Criamos um pet com a espécie em branco ("").
        Pet petComEspecieEmBranco = new Pet(
                "Thor",
                "", // Espécie em branco
                "Poodle",
                "Leitura QR Code",
                "Instituto Federal de Pernambuco"
        );

        // Act: Tenta cadastrar o pet com a espécie inválida
        Pet resultadoCadastro = petNegocio.cadastrar(petComEspecieEmBranco, dono);

        // Assert: Verifica se o cadastro foi corretamente rejeitado
        assertNull(resultadoCadastro, "O sistema deveria retornar nulo ao tentar cadastrar um pet com espécie em branco.");

        // Garante que o repositório NUNCA foi chamado.
        verify(petRepository, never()).inserir(any(Pet.class));
    }
}