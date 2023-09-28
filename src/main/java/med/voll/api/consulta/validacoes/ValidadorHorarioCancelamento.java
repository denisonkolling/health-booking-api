package med.voll.api.consulta.validacoes;

import med.voll.api.consulta.ConsultaRepository;
import med.voll.api.consulta.DadosCancelamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioCancelamentoConsulta")
public class ValidadorHorarioCancelamento implements ValidadorCancelamentoConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;


    @Override
    public void validar(DadosCancelamentoConsulta dados) {

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora,consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência de 24h!");
        }


    }
}
