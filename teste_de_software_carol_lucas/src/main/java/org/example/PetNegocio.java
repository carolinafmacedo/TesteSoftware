package org.example;

public class PetNegocio {
    private final PetRepository rep;

    public PetNegocio(PetRepository repository) {
        this.rep = repository;
    }

    public Pet cadastrar(Pet pet, Usuario dono) {
        // Validação 1: Verifica se há um dono logado.
        if (dono == null) {
            return null;
        }

        // Validação 2: Verifica se o pet tem pelo menos um nome.
        if (pet.getNome() == null || pet.getNome().trim().isEmpty()) {
            return null;
        }

        // Validação 3: Verifica se a raça do pet foi preenchida.
        if (pet.getRaca() == null || pet.getRaca().trim().isEmpty()) {
            return null; // Rejeita o cadastro se a raça estiver vazia.
        }
        
        // Validação 4: Verifica se a espécie do pet foi preenchida.
        if (pet.getEspecie() == null || pet.getEspecie().trim().isEmpty()) {
            return null; // Rejeita o cadastro se a espécie estiver vazia.
        }

        // Validação 5: Verifica se o endereço não está em branco.
        if (pet.getEndereco() == null || pet.getEndereco().trim().isEmpty()) {
            return null;
        }

        // Validação 6: Verifica se a leitura do QR Code é válida.
        if (pet.getQrCode() == null || pet.getQrCode().trim().isEmpty()) {
            return null;
        }

        pet.setDonoLogin(dono.getNome());
        return this.rep.inserir(pet);
    }
}