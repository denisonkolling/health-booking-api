package med.voll.api.paciente;

import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroPaciente(String nome, String email, String Telefone, String cpf, DadosEndereco endereco) {
}
