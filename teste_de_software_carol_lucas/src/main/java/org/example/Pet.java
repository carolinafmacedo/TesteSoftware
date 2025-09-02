package org.example;

public class Pet {
    private String nome;
    private String especie;
    private String raca;
    private String qrCode;
    private String endereco;
    private String donoLogin;

    public Pet(String nome, String especie, String raca, String qrCode, String endereco) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.qrCode = qrCode;
        this.endereco = endereco;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getDonoLogin() { return donoLogin; }
    public void setDonoLogin(String donoLogin) { this.donoLogin = donoLogin; }
}