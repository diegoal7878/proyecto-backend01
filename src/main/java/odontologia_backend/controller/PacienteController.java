package odontologia_backend.controller;

import odontologia_backend.entity.Paciente;
import odontologia_backend.repository.PacienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    // LISTAR TODOS LOS PACIENTES
    @GetMapping
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    // BUSCAR PACIENTE POR ID
    @GetMapping("/{id}")
    public Paciente buscarPaciente(@PathVariable Integer id) {
        return pacienteRepository.findById(id)
                .orElse(null);
    }

    // REGISTRAR PACIENTE
    @PostMapping
    public Paciente registrarPaciente(@RequestBody Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    // ACTUALIZAR PACIENTE
    @PutMapping("/{id}")
    public Paciente actualizarPaciente(
            @PathVariable Integer id,
            @RequestBody Paciente datosPaciente) {

        return pacienteRepository.findById(id)
                .map(paciente -> {

                    paciente.setIdUsuario(datosPaciente.getIdUsuario());
                    paciente.setNombres(datosPaciente.getNombres());
                    paciente.setApellidos(datosPaciente.getApellidos());
                    paciente.setDocumento(datosPaciente.getDocumento());
                    paciente.setTelefono(datosPaciente.getTelefono());
                    paciente.setCorreo(datosPaciente.getCorreo());
                    paciente.setCodigoHistoria(datosPaciente.getCodigoHistoria());

                    return pacienteRepository.save(paciente);
                })
                .orElse(null);
    }

    // ELIMINAR PACIENTE
    @DeleteMapping("/{id}")
    public String eliminarPaciente(@PathVariable Integer id) {

        if (!pacienteRepository.existsById(id)) {
            return "Paciente no encontrado";
        }

        pacienteRepository.deleteById(id);
        return "Paciente eliminado correctamente";
    }
}