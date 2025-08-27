package org.example;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private List<Usuario> usuarios = new ArrayList<Usuario>();

    public Usuario inserir(Usuario u) {
        this.usuarios.add(u);
        return u;
    }

    public boolean buscarPorTelefone(String telefone){
        for (Usuario u:usuarios){
            if(u.getTelefone().equals(telefone)){
                return true;
            }
        }
        return false;
    };

    public boolean buscarPorEmail(String email){
        for (Usuario u:usuarios){
            if(u.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    };
}