package com.api.quesIo.clientes.services;

import com.api.quesIo.clientes.Models.ClienteModel;
import com.api.quesIo.clientes.Models.UsuarioModel;
import com.api.quesIo.clientes.Repositorys.UsuarioRepository;
import com.api.quesIo.clientes.dtos.ClienteDto;
import com.api.quesIo.clientes.dtos.UsuarioDto;
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
