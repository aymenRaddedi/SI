package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Employe;

public interface IEmployeService {
    Employe create(Employe entity);
    Employe findById(Long id);
    List<Employe> findAll();
    Employe update(Long id, Employe entity);
    void deleteById(Long id);
}
