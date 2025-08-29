package org.example;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private List<Usuario> usuarios = new ArrayList<>();

    public Usuario inserir(Usuario u) {
        this.usuarios.add(u);
        return u;

    }

    public Usuario findByLogin(String login) {
        // Verifica se o login fornecido não é nulo para evitar erros.
        if (login == null) {
            return null;
        }
        for (Usuario u : usuarios) {
            if (login.equals(u.getNome())) {
                return u;
            }
        }
        return null;


    }

    // Dentro da classe UsuarioRepository
    public Usuario atualizar(Usuario u) {
        // 1. Busca o usuário na lista pelo nome (login)
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioExistente = usuarios.get(i);
            if (usuarioExistente.getNome().equals(u.getNome())) {
                // 2. Se encontrar, remove o antigo e adiciona o novo (atualizado)
                usuarios.set(i, u);
                return u; // Retorna o usuário atualizado
            }
        }
        // Se não encontrar o usuário, retorna nulo
        return null;
    }
    public Usuario buscarPorEmail(String email) {
        if (email == null) {
            return null;
        }
        for (Usuario u : usuarios) {
            if (email.equals(u.getEmail())) {
                return u;
            }
        }
        return null;
    }
}