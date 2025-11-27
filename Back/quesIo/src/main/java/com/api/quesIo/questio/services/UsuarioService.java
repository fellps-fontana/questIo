package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.UsuarioModel;
import com.api.quesIo.questio.Repositorys.UsuarioRepository;
import com.api.quesIo.questio.dtos.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository _usuarioRepository ;
    public UsuarioService(UsuarioRepository usuarioRepository) {
        _usuarioRepository = usuarioRepository ;
    }


    public UsuarioModel create (UsuarioDto usuario )
    {
        UsuarioModel usuarioModel = new UsuarioModel() ;
        String senhaLimpa = usuario.getSenha();
        String senhaCriptografada = passwordEncoder.encode(senhaLimpa);
        usuarioModel.setSenha(senhaCriptografada);
        usuarioModel.setNome(usuario.getNome());
        usuarioModel.setEmail(usuario.getEmail());
        return usuarioModel ;
    }

}
