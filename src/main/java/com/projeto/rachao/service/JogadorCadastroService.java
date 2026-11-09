package com.projeto.rachao.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto.rachao.dto.JogadorCadastroDTO;
import com.projeto.rachao.dto.JogadorDTO;
import com.projeto.rachao.entidades.Jogador;
import com.projeto.rachao.repository.JogadorRepository;


@Transactional
    public Jogador cadastrarJogador(JogadorCadastroDTO dto) {

        // 2. Valida formatos e regras específicas
        if (!isEmailValido(dto.getEmail())) 
            throw new IllegalArgumentException("O formato do e-mail é inválido.");
        
        if (dto.getSenha().length() < 8) 
            throw new IllegalArgumentException("A senha deve possuir no mínimo 8 caracteres.");
        
        if (!isCpfValido(dto.getCpf())) 
            throw new IllegalArgumentException("O CPF informado é inválido.");
        
        // 3. Verifica a unicidade no banco de dados H2
        if (jogadorRepository.existsByEmail(dto.getEmail())) 
            throw new IllegalArgumentException("Este e-mail já está cadastrado no sistema.");
        
        if (jogadorRepository.existsByCpf(dto.getCpf())) 
            throw new IllegalArgumentException("Este CPF já está cadastrado no sistema.");
        
        // 4. Converte o DTO para a Entidade e salva
        Jogador jogador = new Jogador();
        jogador.setNome(dto.getNome());
        jogador.setEmail(dto.getEmail());
        jogador.setSenha(dto.getSenha()); 
        jogador.setCpf(dto.getCpf());
        return jogadorRepository.save(jogador);


    private void validarCamposObrigatorios(JogadorCadastroDTO dto) {
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) 
            throw new IllegalArgumentException("O Nome é obrigatório.");
        
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) 
            throw new IllegalArgumentException("O E-mail é obrigatório.");
        
        if (dto.getSenha() == null || dto.getSenha().trim().isEmpty()) 
            throw new IllegalArgumentException("A Senha é obrigatória.");
        
        if (dto.getCpf() == null || dto.getCpf().trim().isEmpty())
            throw new IllegalArgumentException("O CPF é obrigatório.");
    }

    private boolean isEmailValido(String email) 
    // ^ e $ -> Delimitam o começo e o fim da String
    // [A-Za-z0-9+_.-] -> Permite letras maiusculas "A-Z", Minusculas "a-z", numeros "0-9" e alguns caracteres especiais "+_.-"
    // + -> Precisa ter pelo menos um caractere dessa lista
    // @ -> Cria a obrigatoriedade de ter um @ aqui
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    

private boolean isCpfValido(String cpf) {
        // Proteção contra nulos e strings vazias para evitar NullPointerException
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        // Remove os caracteres especiais, deixando apenas os numeros
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF está com algum tamanho diferente de 11 ou todos repetidos
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            // Cálculo do 1º Dígito Verificador (DV1)
            int soma = 0, peso = 10;
            // Pega os 9 primeiros dígitos e multiplica por pesos decrescentes
            for (int i = 0; i < 9; i++) soma += (cpf.charAt(i) - '0') * peso--;
            
            // Obtém o resto da divisão por 11
            int resto = soma % 11;
            // Se o resto for 0 ou 1, o DV1 é 0. Senão, subtrai de 11.
            char digito1 = (resto == 0 || resto == 1) ? '0' : (char) ((11 - resto) + '0');

            // Cálculo do 2º Dígito Verificador (DV2)
            soma = 0; 
            peso = 11;
            // Pega os 10 primeiros dígitos e multiplica por pesos decrescentes
            for (int i = 0; i < 10; i++) soma += (cpf.charAt(i) - '0') * peso--;
            
            // Obtém o resto da divisão por 11
            resto = soma % 11;
            // Se o resto for 0 ou 1, o DV2 é 0. Senão, subtrai de 11.
            char digito2 = (resto == 0 || resto == 1) ? '0' : (char) ((11 - resto) + '0');

            // Verifica se os dígitos calculados conferem com os passados no CPF
            return (digito1 == cpf.charAt(9)) && (digito2 == cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }