package med.voll.api.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario loadUserByUsername(String username) throws Exception {
        return repository.findByLogin(username);

    }
}
