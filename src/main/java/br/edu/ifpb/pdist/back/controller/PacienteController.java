package br.edu.ifpb.pdist.back.controller;

import br.edu.ifpb.pdist.back.model.Paciente;
import br.edu.ifpb.pdist.back.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;
   
    // Ativa o menu Paciente na barra de navegação
    @ModelAttribute("menu")
    public String activeMenu(){
        return "paciente";
    }

    // Rota para acessar a lista pelo menu
    @RequestMapping(method = RequestMethod.GET)
    public List<Paciente> listAll(ModelAndView mav) {
        List<Paciente> opPacientes = pacienteRepository.findAll();
        return opPacientes;
    } 

    // Rota para acessar a lista ao usar o REDIRECT
    @RequestMapping("/")
    public List<Paciente> listAll(Model model) {
       return pacienteRepository.findAll();
    }

    // Rota para acessar o formunário
    @RequestMapping("/formPaciente")
    public ModelAndView getFormPaciente(Paciente paciente, ModelAndView mav) {
        mav.addObject("paciente", paciente);
        mav.setViewName("paciente/formPaciente");
        return mav;
    }

    // Rota para cadastrar um Paciente no Sitema
    @RequestMapping(value="/save", method = RequestMethod.POST)
    public ResponseEntity<Paciente> save(@RequestBody Paciente paciente, RedirectAttributes redAttrs) {
        Optional<Paciente> OpPaciente = pacienteRepository.findByCpf(paciente.getCpf());
        if (!OpPaciente.isPresent()){       
            Paciente novoPaciente = pacienteRepository.save(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente); 
        }
        return null;
   }

    // Rota para preencer os dados do formunlário de atualização com dados do banco 
    @RequestMapping("/{id}")
    public Paciente getPacienteById(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        Optional<Paciente> opPaciente = pacienteRepository.findById(id);
        if (opPaciente.isPresent()) {
            Paciente paciente = opPaciente.get();
            return paciente;
        } 
        return null;
    }
    
    // Rota para atualizar um Paciente na lista pelo formUpPaciente
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ResponseEntity<Paciente> update(@RequestBody Paciente paciente, RedirectAttributes redAttrs) {
        Paciente upDatePaciente = pacienteRepository.save(paciente);
        return ResponseEntity.status(HttpStatus.OK).body(upDatePaciente); 
    }

    // Rota para deletar um Paciente da lista
    @RequestMapping("/delete/{id}")
    public void excluirpaciente(@PathVariable(value = "id") Integer id) {
        Optional<Paciente> OpPaciente = pacienteRepository.findById(id);
        if (OpPaciente.isPresent()){
            Paciente paciente = OpPaciente.get();
            pacienteRepository.delete(paciente);
        }
    }
}
