package org.example;

public class UsuarioNegocio {
    private UsuarioRepository rep;

    public UsuarioNegocio(UsuarioRepository repo) {
        this.rep = repo;
    }

    public Usuario adicionar(Usuario u) {
        if (u.getNome() == null || u.getNome().trim().isEmpty() || u.getNome().contains(" ")) {
            return null;
        }
        if (u.getEmail() == null || u.getEmail().trim().isEmpty() || !u.getEmail().contains("@")) {
            return null;
        }
        if (u.getSenha() == null || u.getSenha().trim().isEmpty()) {
            return null;
        }
        return this.rep.inserir(u);
    }

    public Usuario logar(String login, String senha) {
        Usuario usuario = this.rep.findByLogin(login);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;
    }

    public Usuario atualizar(Usuario usuario) {
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

    public Usuario recuperarSenha(String email, String novaSenha) {
        // Validação 1: Nova senha não pode ser em branco.
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            return null;
        }

        // Validação 2: Busca o usuário pelo e-mail.
        Usuario usuario = this.rep.buscarPorEmail(email);
        if (usuario == null) {
            return null; // Retorna nulo se o e-mail não for encontrado.
        }

        // Se o usuário existe e a nova senha é válida, atualiza a senha.
        usuario.setSenha(novaSenha);
        return this.rep.atualizar(usuario);
    }
}
