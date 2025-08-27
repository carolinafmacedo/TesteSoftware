package org.example;

public class UsuarioNegocio {
    private UsuarioRepository rep;
    public UsuarioNegocio(UsuarioRepository repo){
        this.rep=repo;
    }

    public Usuario adicionar(Usuario u){
        if(u.getTelefone() == null || u.getTelefone().trim().isEmpty()){
            return null;
        }
        if(this.rep.buscarPorTelefone(u.getTelefone())){
            return null;
        }
        if(this.rep.buscarPorEmail(u.getEmail())){
            return null;
        }
        if(u.getNome() == null || u.getNome().trim().isEmpty()){
            return null;
        }
        if(u.getEndereco() == null || u.getEndereco().trim().isEmpty()){
            return null;
        }
        if(u.getDataNascimento() == null ){
            return null;
        }
        if(u.getEmail() == null || u.getEmail().trim().isEmpty()){
            return null;
        }
        return this.rep.inserir(u);
    }
}