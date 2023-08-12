package net.sidibrahim.Hospital.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.sidibrahim.Hospital.entities.Patient;
import net.sidibrahim.Hospital.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "5") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String keyword){
        Page<Patient> pagePatient=patientRepository.chercher("%"+keyword+"%",PageRequest.of(page,size));
        model.addAttribute("pagePatient", pagePatient.getContent());
        model.addAttribute("pages",new int[pagePatient.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patient";
    }
    @GetMapping("/delete")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/index?keyword="+keyword+"&page="+page;
    }
    @GetMapping("/")
    String home(){
        return "redirect:/index";
    }


@GetMapping("/formPatient")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatient";
    }
    @PostMapping("/savePatient")
    public String save(@Valid Patient patient, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "formPatient";
        }
        patientRepository.save(patient);
        return "redirect:/index?keyword="+patient.getNom();
    }
    @GetMapping("/editPatient")
    public String editPatient(Model model,@RequestParam(name = "id") Long id){
        Patient patient = patientRepository.findById(id).get();
        model.addAttribute("patient",patient);
        return "editPatient";
    }


}
