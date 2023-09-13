package med.voll.api.consulta;

import med.voll.api.medico.Medico;
import med.voll.api.paciente.Paciente;

import java.time.LocalDateTime;

public record DadosListagemConsulta(Long id, Medico medico, Paciente paciente, LocalDateTime data) {

    public DadosListagemConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico(), consulta.getPaciente(), consulta.getData());
    }
}
