package org.example;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private List<Usuario> usuarios = new ArrayList<>();

    public Usuario inserir(Usuario u) {
        this.usuarios.add(u);
        return u;
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