package org.example;

// Não precisamos mais de 'LocalDate' ou 'DateTimeFormatter'
public class Usuario {
    private String nome;
    private String email;
    private String senha;

    // NOVO CONSTRUTOR: Simples, direto e focado no que importa.
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters essenciais
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}