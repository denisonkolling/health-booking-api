package med.voll.api.consulta;

import med.voll.api.consulta.validacoes.ValidadorAgendamentoConsulta;
import med.voll.api.consulta.validacoes.ValidadorCancelamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorAgendamentoConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) throws Exception {

        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new Exception("Id do paciente informado não existe!");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new Exception("Id do médico informado não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.findById(dados.idPaciente()).get();

        var medico = escolherMedico(dados);

        if (medico == null) {
            throw new Exception("Não existe médico disponível nessa data!");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    public void cancelar(DadosCancelamentoConsulta dados) {

        if(!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("Id da consulta não existe!");
        }

        validadoresCancelamento.forEach(v->v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());


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