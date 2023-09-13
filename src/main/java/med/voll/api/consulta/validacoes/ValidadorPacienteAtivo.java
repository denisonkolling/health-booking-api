package med.voll.api.consulta.validacoes;

import med.voll.api.consulta.DadosAgendamentoConsulta;
import med.voll.api.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo {

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados) throws Exception{

        var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());

        if (!pacienteEstaAtivo) {
            throw new Exception("Consulta só pode ser agendada por paciente ativo");
        }
    }
}
