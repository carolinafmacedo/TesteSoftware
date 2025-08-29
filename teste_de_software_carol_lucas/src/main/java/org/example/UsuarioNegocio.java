package org.example;

public class UsuarioNegocio {
    private UsuarioRepository rep;

    public UsuarioNegocio(UsuarioRepository repo) {
        this.rep = repo;
    }

    public Usuario adicionar(Usuario u) {
        // Validação 1: O login (nome) não pode ser nulo ou vazio.
        if (u.getNome() == null || u.getNome().trim().isEmpty()) {
            return null;
        }
        // Validação 1.1: O login (nome) não pode conter espaços.
        if (u.getNome().contains(" ")) {
            return null;
        }

        // Validação 2: O e-mail não pode ser nulo ou vazio.
        if (u.getEmail() == null || u.getEmail().trim().isEmpty()) {
            return null;
        }

        // Validação 3: A senha não pode ser nula ou vazia.
        if (u.getSenha() == null || u.getSenha().trim().isEmpty()) {
            return null;
        }

        // Validação 4: O e-mail deve ter um formato válido (conter "@").
        if (!u.getEmail().contains("@")) {
            return null;
        }

        // Se todas as validações passarem, insere o usuário.
        return this.rep.inserir(u);

    }

    public Usuario logar(String login, String senha) {
        // 1. Busca o usuário no banco de dados pelo login
        Usuario usuario = this.rep.findByLogin(login);

        // 2. Verifica se o usuário foi encontrado e se a senha bate
        if (usuario != null && usuario.getSenha().equals(senha)) {
            // Sucesso! Retorna o usuário encontrado.
            return usuario;
        }

        // Se o usuário não existe ou a senha está incorreta, retorna null.
        return null;
    }

    public Usuario atualizar(Usuario usuario) {
        // Lógica de validação (pode ser expandida no futuro)
        if (usuario == null || usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            return null;
        }
        if (!usuario.getEmail().contains("@")) {
            return null;
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            return null;
        }
        return this.rep.atualizar(usuario);
    }
}
