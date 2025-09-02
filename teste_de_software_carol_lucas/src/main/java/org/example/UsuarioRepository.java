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

    public Usuario atualizar(Usuario u) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioExistente = usuarios.get(i);
            if (usuarioExistente.getNome().equals(u.getNome())) {
                usuarios.set(i, u);
                return u;
            }
        }
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