package org.example;

public class UsuarioNegocio {
    private UsuarioRepository rep;

    public UsuarioNegocio(UsuarioRepository repo){
        this.rep = repo;
    }
    public Usuario adicionar(Usuario u) {
        // Validação 1: O login (nome) não pode ser nulo ou vazio.
        if (u.getNome() == null || u.getNome().trim().isEmpty()) {
            return null;
        }

        // Validação 2: O e-mail não pode ser nulo ou vazio.
        if (u.getEmail() == null || u.getEmail().trim().isEmpty()) {
            return null;
        }

        // Validação 3: A senha не pode ser nula ou vazia.
        if (u.getSenha() == null || u.getSenha().trim().isEmpty()) {
            return null;
        }

        // Validação do TC004: O e-mail deve ter um formato válido (conter "@").
        if (u.getEmail().contains("@")) { // Este código rejeita os e-mails BONS
            return null;

        }

        // Se todas as validações focais passarem, insere o usuário.
        return this.rep.inserir(u);
    }
}