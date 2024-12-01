package com.dsw_pin.book_list.controller;

import com.dsw_pin.book_list.dtos.UserRecordDto;
import com.dsw_pin.book_list.model.Sessao;
import com.dsw_pin.book_list.model.User;
import com.dsw_pin.book_list.repositories.SessaoRepository;
import com.dsw_pin.book_list.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessaoRepository sessaoRepository;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Verifique se os valores não são nulos
        if (email == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email e senha são obrigatórios.");
        }

        // Busca o usuário no banco de dados pelo email
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Verifica a senha
            if (BCrypt.checkpw(password, user.getPassword())) {
                // Cria a sessão manualmente
                String sessionId = UUID.randomUUID().toString();
                Sessao sessao = new Sessao();
                sessao.setId(sessionId);
                sessao.setDataCriacao(new Date());
                sessao.setValida(true);
                sessaoRepository.save(sessao);

                Map<String, String> response = new HashMap<>();
                response.put("sessionId", sessionId);
                response.put("userType", user.getTipo().toString());
                response.put("userId", user.getId().toString());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRecordDto userRecordDto) {
        // Verifica se o email já está cadastrado
        if (userRepository.findByEmail(userRecordDto.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já está em uso.");
        }

        // Verifica o comprimento da senha
        if (userRecordDto.password().length() < 6) {
            return ResponseEntity.badRequest().body("A senha deve ter pelo menos 6 caracteres.");
        }

        // Criptografa a senha
        String hashedPassword = BCrypt.hashpw(userRecordDto.password(), BCrypt.gensalt());

        // Cria um novo usuário
        User newUser = new User();
        newUser.setEmail(userRecordDto.email());
        newUser.setName(userRecordDto.name());
        newUser.setPassword(hashedPassword);
        newUser.setTipo(User.TipoUsuario.valueOf(userRecordDto.tipo()));

        try {
            userRepository.save(newUser);
            return ResponseEntity.ok("Usuário registrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar o usuário.");
        }
    }
}