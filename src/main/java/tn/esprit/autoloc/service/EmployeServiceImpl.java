package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {
    private final IEmployeRepository repository;

    private void validate(Employe entity) {
        if (entity.getNom() == null || entity.getNom().isBlank() || entity.getPrenom() == null || entity.getPrenom().isBlank() || entity.getRole() == null) throw new IllegalArgumentException("Invalid Employe data");
    }

    @Override
    public Employe create(Employe entity) {
        if (entity.getIdEmploye() != null) throw new IllegalArgumentException("A new Employe must not have an id");
        validate(entity);
        return repository.save(entity);
    }

    @Override
    public Employe findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() { return repository.findAll(); }

    @Override
    public Employe update(Long id, Employe entity) {
        Employe existing = findById(id);
        validate(entity);
        existing.setNom(entity.getNom());
        existing.setPrenom(entity.getPrenom());
        existing.setRole(entity.getRole());
        existing.setAgence(entity.getAgence());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Employe", id);
        repository.deleteById(id);
    }
}
