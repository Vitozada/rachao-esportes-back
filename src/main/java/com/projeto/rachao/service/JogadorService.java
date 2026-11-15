package com.projeto.rachao.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projeto.rachao.dto.JogadorCadastroDTO;
import com.projeto.rachao.dto.JogadorDTO;
import com.projeto.rachao.dto.JogadorPerfilDTO;

import com.projeto.rachao.entidades.Jogador;

import com.projeto.rachao.repository.JogadorRepository;
import com.projeto.rachao.repository.PartidaRepository;
import com.projeto.rachao.repository.InscricaoRepository;
import com.projeto.rachao.repository.DesempenhoRepository;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final PasswordEncoder passwordEncoder;
    private final PartidaRepository partidaRepository;
    private final InscricaoRepository inscricaoRepository;
    private final DesempenhoRepository desempenhoRepository;

    // INJEÇÃO DE DEPENDÊNCIAS
    public JogadorService(
            JogadorRepository jogadorRepository,
            PasswordEncoder passwordEncoder,
            PartidaRepository partidaRepository,
            InscricaoRepository inscricaoRepository,
            DesempenhoRepository desempenhoRepository) {

        this.jogadorRepository = jogadorRepository;
        this.passwordEncoder = passwordEncoder;
        this.partidaRepository = partidaRepository;
        this.inscricaoRepository = inscricaoRepository;
        this.desempenhoRepository = desempenhoRepository;
    }

    // Se todos os campos foram preenchidos
    private void validarCamposObrigatorios(JogadorCadastroDTO dados) {

        if (dados == null)
            throw new IllegalArgumentException("Os dados do cadastro são obrigatórios.");

        if (dados.getNome() == null || dados.getNome().trim().isEmpty())
            throw new IllegalArgumentException("O nome é obrigatório.");

        if (dados.getEmail() == null || dados.getEmail().trim().isEmpty())
            throw new IllegalArgumentException("O e-mail é obrigatório.");

        if (dados.getSenha() == null || dados.getSenha().trim().isEmpty())
            throw new IllegalArgumentException("A senha é obrigatória.");

        if (dados.getDataNascimento() == null)
            throw new IllegalArgumentException("A data de nascimento é obrigatória.");
    }

    // Validação de Idade
    private void validarMaioridade(LocalDate dataNascimento) {

        // Reforçar regras de negócio e impedir que o método rode com uma variável
        // inexistente
        if (dataNascimento == null)
            throw new IllegalArgumentException("A data de nascimento é obrigatória.");

        // ADIÇÃO: Define o fuso horário de São Paulo para evitar erros de virada de dia
        // em servidores UTC
        LocalDate hoje = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        // Impede datas futuras
        if (dataNascimento.isAfter(hoje))
            throw new IllegalArgumentException("A data de nascimento não pode ser futura.");

        // O objetivo é validar quanto tempo se passou entre a data de nascimento até o
        // dia de hoje
        int idade = Period.between(dataNascimento, hoje).getYears();

        // Se a idade for menor que 18, ele não deixa criar
        if (idade < 18)
            throw new IllegalArgumentException("O jogador deve ter pelo menos 18 anos para se cadastrar.");
    }

    // método de validação de e-mail
    private boolean isEmailValido(String email) {
        // ADIÇÃO: Proteção contra null para manter o padrão defensivo
        if (email == null)
            return false;

        // Ele valida se o e-email é valido a partir dessa lista de caracteres
        // ^ e $ delimitam o começo e o fim da String
        // [A-Za-z0-9_.] representa a lista de caracteres que podem ser inseridos antes
        // do @
        // O + representa que deve ter pelo menos um desses caracteres da lista antes do
        // @ e após ele
        // O @ faz com que ele seja um caractere obrigatório entre as strings
        // ADIÇÃO: Regex atualizada para aceitar hífen (-), sinal de mais (+) e exigir
        // extensão de domínio (ex: .com)
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    // Método de verificação do CPF
    private boolean isCpfValido(String cpf) {

        if (cpf == null)
            return false;

        // Remove caracteres especiais
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica quantidade de dígitos e números repetidos (ex: 111.111.111-11)
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}"))
            return false;

        int soma = 0;
        int peso = 10;

        // Loop para validação do primeiro digito veriricador
        for (int i = 0; i < 9; i++)
            soma += (cpf.charAt(i) - '0') * peso--;
        int resto = soma % 11;
        // se o resto for menor que dois, o primeiro digito deve ser 0
        // caso contrário, deverá vir do resultado de 11 - o resto
        int digito1 = (resto < 2) ? 0 : 11 - resto;

        // restauração das variáveis para o cálculo do segundo dígito
        soma = 0;
        peso = 11;

        for (int i = 0; i < 10; i++)
            soma += (cpf.charAt(i) - '0') * peso--;
        resto = soma % 11;

        int digito2 = (resto < 2) ? 0 : 11 - resto;

        // Compara os resultados dos calculos, caso os dois sejam coerentes, o cpf será
        // considerado válido
        // e o método retornará true
        return digito1 == (cpf.charAt(9) - '0') && digito2 == (cpf.charAt(10) - '0');
    }

    // Método para criar um registro de cadastro de um jogador
    public JogadorDTO cadastrar(JogadorCadastroDTO dto) {

        // Verifica os campos obrigatórios
        validarCamposObrigatorios(dto);

        // Padroniza o e-mail
        String email = dto.getEmail().trim().toLowerCase();

        // Chama o método de validar e-mail e verifica se o resultado é true
        if (!isEmailValido(email))
            throw new IllegalArgumentException("E-mail inválido.");

        // Valida o tamanho da senha
        if (dto.getSenha().length() < 8)
            throw new IllegalArgumentException("A senha deve ter no mínimo 8 caracteres.");

        // CHama o metodo de validação de idade
        validarMaioridade(dto.getDataNascimento());

        // Consulta o banco de dados e vê se alguém já criou uma conta com este email
        if (jogadorRepository.existsByEmail(email))
            throw new IllegalArgumentException("E-mail já cadastrado.");

        // Se tudo estiver correto, crie o jogador caro
        Jogador jogador = new Jogador();

        jogador.setNome(dto.getNome().trim());
        jogador.setEmail(email);
        jogador.setDataNasc(dto.getDataNascimento());
        // Criptografia da senha
        jogador.setSenha(passwordEncoder.encode(dto.getSenha()));

        // Salva no banco de dados
        Jogador salvo = jogadorRepository.save(jogador);

        return converterParaDTO(salvo);
    }

    // ATUALIZAR PERFIL
    public JogadorDTO atualizarPerfil(Long id, JogadorPerfilDTO dto) {
        if (dto == null)
            throw new IllegalArgumentException("Os dados do perfil são obrigatórios.");

        // Busca o jogador
        Jogador jogador = buscarEntidadePorId(id);

        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {

            String cpf = dto.getCpf().replaceAll("[^0-9]", "");

            if (!isCpfValido(cpf))
                throw new IllegalArgumentException("CPF inválido.");

            // Impede CPF duplicado
            if (!cpf.equals(jogador.getCpf()) && jogadorRepository.existsByCpf(cpf))
                throw new IllegalArgumentException("CPF já cadastrado.");

            jogador.setCpf(cpf);
        }

        // VALIDAÇÃO DE CEP
        if (dto.getCep() != null && !dto.getCep().isBlank()) {

            String cep = dto.getCep().replaceAll("[^0-9]", "");

            if (cep.length() != 8) 
                throw new IllegalArgumentException("O CEP deve conter 8 dígitos.");

            jogador.setCep(cep);
        }

        // VALIDAÇÃO DE DESCRIÇÃO
        if (dto.getDescricao() != null) {

            if (dto.getDescricao().length() > 500)
                throw new IllegalArgumentException("A descrição deve ter no máximo 500 caracteres.");

            // .trim() para remover espaços desnecessários no início e no fim
            jogador.setDescricao(dto.getDescricao().trim());
        }

        // VALIDAÇÃO DE ALTURA
        if (dto.getAltura() != null) {

            if (!Double.isFinite(dto.getAltura()) || dto.getAltura() <= 0 || dto.getAltura() > 3)
                throw new IllegalArgumentException("Altura inválida.");

            jogador.setAltura(dto.getAltura());
        }

        // Salva as alterações
        Jogador salvo = jogadorRepository.save(jogador);

        return converterParaDTO(salvo);
    }

    // Método auxiliar para buscar a entidade Jogador pelo ID, lançando uma exceção caso não seja encontrado
    private Jogador buscarEntidadePorId(Long id) {
        return jogadorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Jogador não encontrado."));
    }

    // Método para buscar jogador por ID
    public JogadorDTO buscarPorId(Long id) {
        Jogador jogador = buscarEntidadePorId(id);
        return converterParaDTO(jogador);
    }

    // LISTAR TODOS OS JOGADORES
    public List<JogadorDTO> listar() {
        return jogadorRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    // EXCLUIR JOGADOR
    public void excluir(Long id) {
        // Busca o jogador
        Jogador jogador = buscarEntidadePorId(id);

        // Verifica se existem registros associados
        if (partidaRepository.existsByJogadorHostId(id) || inscricaoRepository.existsByJogadorId(id) || desempenhoRepository.existsByJogadorId(id))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O jogador possui partidas ou registros associados e não pode ser excluído.");
 
        // Exclui o jogador
        jogadorRepository.delete(jogador);
    }

    // CONVERTER ENTIDADE PARA DTO
    private JogadorDTO converterParaDTO(Jogador jogador) {

        JogadorDTO dto = new JogadorDTO();

        dto.setId(jogador.getId());
        dto.setNome(jogador.getNome());
        dto.setEmail(jogador.getEmail());
        dto.setCep(jogador.getCep());
        dto.setDataNascimento(jogador.getDataNasc());
        dto.setDescricao(jogador.getDescricao());
        dto.setAltura(jogador.getAltura());

        return dto;
    }
}