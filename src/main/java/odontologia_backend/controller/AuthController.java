package odontologia_backend.controller;

import odontologia_backend.entity.Usuario;
import odontologia_backend.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest datos) {

        Usuario usuario = usuarioRepository.findByCorreo(datos.email())
                .orElse(null);

        System.out.println("Correo recibido: " + datos.email());
        System.out.println("Contraseña recibida: " + datos.password());

        if (usuario == null) {
            System.out.println("❌ Usuario NO encontrado");
            return "Correo o contraseña incorrectos";
        }

        System.out.println("Usuario encontrado: " + usuario.getNombreUsuario());
        System.out.println("Contraseña BD: " + usuario.getContrasena());

        if (!usuario.getContrasena().equals(datos.password())) {
            return "Correo o contraseña incorrectos";
        }

        if (!usuario.getEstado()) {
            return "Usuario inactivo";
        }

        System.out.println("Usuario autenticado: " + usuario.getNombreUsuario());
        System.out.println("Rol: " + usuario.getIdRol());

        return "Inicio de sesión correcto";
    }

    public record LoginRequest(
            String email,
            String password
    ) {}
}