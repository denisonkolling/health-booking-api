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

        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity <List<DadosListagemConsulta>> listar(){
        var listaConsultas = consultaRepository.findAll().stream().map(DadosListagemConsulta::new).toList();
        return ResponseEntity.ok(listaConsultas);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }


}
