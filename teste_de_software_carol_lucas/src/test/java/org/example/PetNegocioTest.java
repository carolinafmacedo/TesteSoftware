package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class PetNegocioTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetNegocio petNegocio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    // TC_012: Realizar cadastro de pet no sistema com dados válidos
    public void testCadastrarPetComDadosValidos_TC012() {
        Usuario dono = new Usuario("lucas", "lucas@gmail.com", "123");
        Pet petParaCadastrar = new Pet("Thor", "Canina", "Poodle", "Leitura QR Code", "Instituto Federal de Pernambuco");

        when(petRepository.inserir(any(Pet.class))).thenReturn(petParaCadastrar);

        Pet petCadastrado = petNegocio.cadastrar(petParaCadastrar, dono);

        assertNotNull(petCadastrado, "O cadastro deveria ter sucesso e retornar o pet.");
        assertEquals("Thor", petCadastrado.getNome());
        assertEquals(dono.getNome(), petCadastrado.getDonoLogin(), "O pet não foi associado ao dono correto.");
        verify(petRepository, times(1)).inserir(petParaCadastrar);
    }

    @Test
    // TC_0013: Tentar cadastrar pet com nome em branco
    public void testCadastrarPetComNomeEmBranco_TC0013() {
        Usuario dono = new Usuario("lucas", "lucas@gmail.com", "123");

        Pet petComNomeEmBranco = new Pet(
                "",
                "Canina",
                "Poodle",
                "Leitura QR Code",
                "Instituto Federal de Pernambuco"
        );

        Pet resultadoCadastro = petNegocio.cadastrar(petComNomeEmBranco, dono);

        assertNull(resultadoCadastro, "O sistema deveria retornar nulo ao tentar cadastrar um pet com nome em branco.");

        verify(petRepository, never()).inserir(any(Pet.class));
    }

    @Test
    // TC_0014: Tentar cadastrar pet com raça em branco
    public void testCadastrarPetComRacaEmBranco_TC0014() {
        Usuario dono = new Usuario("lucas", "lucas@gmail.com", "123");

        Pet petComRacaEmBranco = new Pet(
                "Thor",
                "Canina",
                "",
                "Leitura QR Code",
                "Instituto Federal de Pernambuco"
        );

        Pet resultadoCadastro = petNegocio.cadastrar(petComRacaEmBranco, dono);

        assertNull(resultadoCadastro, "O sistema deveria retornar nulo ao tentar cadastrar um pet com raça em branco.");

        verify(petRepository, never()).inserir(any(Pet.class));
    }

    @Test
    // TC_0015: Tentar cadastrar pet com espécie em branco
    public void testCadastrarPetComEspecieEmBranco_TC0015() {

        Usuario dono = new Usuario("lucas", "lucas@gmail.com", "123");
        Pet petComEspecieEmBranco = new Pet("Thor", "", "Poodle", "Leitura QR Code", "Instituto Federal de Pernambuco");

        Pet resultadoCadastro = petNegocio.cadastrar(petComEspecieEmBranco, dono);

        assertNull(resultadoCadastro, "O sistema deveria retornar nulo ao tentar cadastrar um pet com espécie em branco.");
        verify(petRepository, never()).inserir(any(Pet.class));
    }

    @Test
    // TC_0016: Tentar cadastrar pet com endereço em branco
    public void testCadastrarPetComEnderecoEmBranco_TC0016() {
        Usuario dono = new Usuario("lucas", "lucas@gmail.com", "123");
        Pet petComEnderecoEmBranco = new Pet(
                "Thor",
                "Canina",
                "Poodle",
                "Leitura QR Code",
                ""
        );

        Pet resultadoCadastro = petNegocio.cadastrar(petComEnderecoEmBranco, dono);

        assertNull(resultadoCadastro, "O sistema deveria retornar nulo ao tentar cadastrar um pet com endereço em branco.");
        verify(petRepository, never()).inserir(any(Pet.class));
    }

    @Test
    // TC_0017: Tentar cadastrar pet com QR Code inválido
    public void testCadastrarPetComQrCodeInvalido_TC0017() {
        Usuario dono = new Usuario("lucas", "lucas@gmail.com", "123");
        Pet petComQrCodeInvalido = new Pet(
                "Thor",
                "Canina",
                "Poodle",
                "",
                "Instituto Federal de Pernambuco"
        );

        Pet resultadoCadastro = petNegocio.cadastrar(petComQrCodeInvalido, dono);

        assertNull(resultadoCadastro, "O sistema deveria retornar nulo ao tentar cadastrar um pet com QR Code inválido.");
        verify(petRepository, never()).inserir(any(Pet.class));
    }
}