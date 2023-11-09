package br.edu.ifpb.pdist.back.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Paciente implements Serializable {

    // Para garantir que a assinatura de um número seja única , para o uso do @Id
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nome;

    private String cpf;

    private String sexo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    @Past(message = "Data deve ser no passado")
    private Date dataNascimento;

    private String email;

    private String telefone;

    // Relação entre Paciente e Anamnese
    private String anamnese;
    //@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    //@JoinColumn(name = "id_anamnese") // identificar coluna apenas no ManyToOne
    //@ToString.Exclude
    //private Set<Anamnese> anamineses = new HashSet<Anaminese>(); 

    // Relação entre Paciente e Prontuário
    private String prontuario;
    //@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    //@JoinColumn(name = "id_prontuario") // identificar coluna apenas no ManyToOne
    //@ToString.Exclude
    //private Set<Prontuario> prontuarios = new HashSet<Prontuario>(); 

    public void setName(String name) {
        this.nome = name;
    }

    public void setAnamnese(String anamnese) {
        this.anamnese = anamnese;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCpf(String cpf) {
        if(cpf.length() != 11)
            throw new IllegalArgumentException("CPF deve conter 11 digitos!");
        if(cpf.isBlank())
            throw new IllegalArgumentException("CPF nao pode ser vazio!");
        if(cpf.matches(".*[a-zA-Z]+.*"))
            throw new IllegalArgumentException("CPF nao pode conter caracteres especiais!");
        this.cpf = cpf;
    }

    public void setData_de_nascimento(Date data_de_nascimento) {
        this.dataNascimento = data_de_nascimento;
    }

    public void setEmail(String email) {
        if(email.isBlank())
            throw new IllegalArgumentException("Email nao pode ser vazio!");
        if(!email.contains("@"))
            throw new IllegalArgumentException("Email deve conter @!");
        this.email = email;
    }

    public Paciente(String name, String telefone, String sexo, String email, String cpf) {
        this.nome = name;
        this.telefone = telefone;
        this.sexo = sexo;
        this.email = email;
        this.cpf = cpf;
    }
}
