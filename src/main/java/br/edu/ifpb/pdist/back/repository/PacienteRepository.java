package br.edu.ifpb.pdist.back.repository;

import  br.edu.ifpb.pdist.back.model.Paciente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByCpf(String string);

}
