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
   
    // Ativa o menu Médico na barra de navegação
    @ModelAttribute("menu")
    public String activeMenu(){
        return "medico";
    }

    // Rota para acessar a lista pelo menu
    @RequestMapping(method = RequestMethod.GET)
    public List<Paciente> listAll(ModelAndView mav) {
        List<Paciente> opMedicos = medicoRepository.findAll();
        return opMedicos;
    } 

    // Rota para acessar a lista ao usar o REDIRECT
    @RequestMapping("/")
    public List<Paciente> listAll(Model model) {
       return medicoRepository.findAll();
    }

    // Rota para acessar o formunário
    @RequestMapping("/formMedico")
    public ModelAndView getFormEstu(Paciente medico, ModelAndView mav) {
        mav.addObject("medico", medico);
        mav.setViewName("medico/formMedico");
        return mav;
    }

    // Rota para cadastrar um Médico no Sitema
    @RequestMapping(value="/save", method = RequestMethod.POST)
    public ResponseEntity<Paciente> save(@RequestBody Paciente medico, RedirectAttributes redAttrs) {
        Optional<Paciente> OpMedico = medicoRepository.findByCrm(medico.getCrm());
        if (!OpMedico.isPresent()){       
            Paciente novoMedico = medicoRepository.save(medico);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoMedico); 
        }
        return null;
   }

    // Rota para preencer os dados do formunlário de atualização com dados do banco 
    @RequestMapping("/{id}")
    public Paciente getMedicoById(@PathVariable(value = "id") Integer id, ModelAndView mav) {
        Optional<Paciente> opMedico = medicoRepository.findById(id);
        if (opMedico.isPresent()) {
            Paciente medico = opMedico.get();
            return medico;
        } 
        return null;
    }
    
    // Rota para atualizar um Médico na lista pelo formUpMedico
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ResponseEntity<Paciente> update(@RequestBody Paciente medico, RedirectAttributes redAttrs) {
        Paciente upDateMedico = medicoRepository.save(medico);
        return ResponseEntity.status(HttpStatus.OK).body(upDateMedico); 
    }

    // Rota para deletar um Médico da lista
    @RequestMapping("/delete/{id}")
    public void excluirMedico(@PathVariable(value = "id") Integer id) {
        Optional<Paciente> OpMedico = medicoRepository.findById(id);
        if (OpMedico.isPresent()){
            Paciente medico = OpMedico.get();
            medicoRepository.delete(medico);
        }
    }
}
