package med.voll.api.paciente;

import med.voll.api.endereco.Endereco;

public record DadosListagemPaciente(Long id, String nome, String email, String telefone, Endereco endereco) {
    public DadosListagemPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getCpf(), paciente.getTelefone(), paciente.getEndereco());

    }
}
