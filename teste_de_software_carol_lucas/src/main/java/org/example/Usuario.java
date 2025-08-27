package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Usuario {
    private String nome;
    private String endereco;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private String senha;

    public Usuario(String nome,String endereco, String dataNascimento,String email, String telefone,String senha){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.nome=nome;
        this.endereco=endereco;
        if(dataNascimento != null && !dataNascimento.trim().isEmpty()) {
            this.dataNascimento=LocalDate.parse(dataNascimento,formatter);
        } else {
            this.dataNascimento = null;
        }
        this.email=email;
        this.telefone=telefone;
        this.senha=senha;
    }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}