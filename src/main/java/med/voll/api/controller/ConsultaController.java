package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consultas")

public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) throws Exception {
        agenda.agendar(dados);
        return ResponseEntity.ok(new DadosAgendamentoConsulta(dados.idMedico(), dados.idPaciente(), dados.data(), dados.especialidade()));
    }

    @GetMapping
    public List<DadosListagemConsulta> listar(){
        return consultaRepository.findAll().stream().map(DadosListagemConsulta::new).toList();
    }

//    public List<DadosListagemMedico> listar() {
//        return repository.findAll().stream().map(DadosListagemMedico::new).toList();
//    }

}
