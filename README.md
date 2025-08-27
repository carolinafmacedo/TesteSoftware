# Projeto de Teste de Software: PetGuard App

Este projeto foi desenvolvido como parte da avaliação da disciplina de Teste de Software. O objetivo é aplicar conceitos teóricos e práticos de teste de software na verificação e validação de uma aplicação fictícia, o "PetGuard".

## 1. Sobre o PetGuard

O PetGuard é uma aplicação web pensada para tutores de animais de estimação. Sua principal funcionalidade é permitir que os usuários cadastrem seus pets e associem a eles um QR Code. Em caso de perda do animal, qualquer pessoa que o encontre pode escanear o código para obter as informações de contato do tutor, facilitando a devolução do pet em segurança.

### Funcionalidades Principais

Com base nos requisitos da aplicação, as seguintes funcionalidades foram implementadas e testadas:

* **Gerenciamento de Usuário:**
    * Cadastro de novos usuários no sistema.
    * Login de usuários com credenciais válidas.
    * Atualização das informações do perfil do usuário.
    * Recuperação de senha por e-mail.

* **Gerenciamento de Pets:**
    * Cadastro de novos pets, associando informações como nome, espécie, raça e endereço.
    * Associação de um QR Code único para cada pet cadastrado.

## 2. Objetivo dos Testes

O principal objetivo deste projeto é garantir a qualidade e a confiabilidade do sistema PetGuard através de um plano de testes abrangente. Buscamos:

* **Verificar a funcionalidade:** Garantir que todos os requisitos funcionais, como cadastro, login e gerenciamento de pets, operem conforme o esperado.
* **Identificar defeitos:** Encontrar e documentar bugs e comportamentos inesperados no sistema.
* **Validar a lógica de negócio:** Assegurar que as regras de validação (campos obrigatórios, formatos de e-mail, etc.) estejam corretamente implementadas.
* **Aplicar técnicas de teste:** Utilizar testes de unidade e mocks para isolar componentes e testar a lógica de negócio de forma independente da camada de persistência.

## 3. Tecnologias e Ferramentas

Para a construção e execução dos testes, as seguintes tecnologias foram utilizadas:

* **Linguagem:** Java
* **Framework de Teste:** JUnit 5
* **Framework de Mocks:** Mockito
* **Gerenciador de Dependências:** Gradle

## 4. Estrutura do Projeto

O projeto segue a estrutura padrão de projetos Gradle/Java:

.
├── build.gradle              # Arquivo de configuração do Gradle
└── src
├── main
│   └── java
│       └── org
│           └── example
│               ├── Usuario.java
│               ├── UsuarioNegocio.java
│               └── UsuarioRepository.java
└── test
└── java
└── org
└── example
└── UsuarioNegocioTest.java


* `src/main/java`: Contém o código-fonte da aplicação.
* `src/test/java`: Contém os códigos de teste automatizados.

## 5. Como Executar os Testes

Para executar os testes automatizados, certifique-se de ter o Java JDK instalado. Em seguida, siga os passos:

1.  Clone ou faça o download deste repositório.
2.  Abra um terminal (Prompt de Comando, PowerShell, etc.) na pasta raiz do projeto (`teste_software_carol_lucas`).
3.  Execute o seguinte comando:

    * No Windows:
        ```bash
        gradlew.bat test
        ```
    * No Linux ou macOS:
        ```bash
        ./gradlew test
        ```

O Gradle se encarregará de baixar as dependências e executar todos os testes definidos na pasta `src/test/java`. Um relatório dos resultados será gerado no diretório `build/reports/tests/test/`.

## 6. Equipe do Projeto

* Carolina Macedo
* Lucas Gabriel
---
