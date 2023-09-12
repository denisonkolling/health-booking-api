package med.voll.api.consulta;

import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public void agendar(DadosAgendamentoConsulta dados) throws Exception {

        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new Exception("Id do paciente informado não existe!");
        }

        var paciente = pacienteRepository.findById(dados.idPaciente()).get();

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new Exception("Id do médico informado não existe!");
        }

        var medico = escolherMedico(dados);

        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) throws Exception {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new Exception("Especialidade é obrigatória quando médico não for escolhido!");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}