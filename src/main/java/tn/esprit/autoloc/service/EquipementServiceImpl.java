package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {
    private final IEquipementRepository repository;

    private void validate(Equipement entity) {
        if (entity.getLibelle() == null || entity.getLibelle().isBlank()) throw new IllegalArgumentException("Invalid Equipement data");
    }

    @Override
    public Equipement create(Equipement entity) {
        if (entity.getIdEquipement() != null) throw new IllegalArgumentException("A new Equipement must not have an id");
        validate(entity);
        return repository.save(entity);
    }

    @Override
    public Equipement findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    @Override
    public List<Equipement> findAll() { return repository.findAll(); }

    @Override
    public Equipement update(Long id, Equipement entity) {
        Equipement existing = findById(id);
        validate(entity);
        existing.setLibelle(entity.getLibelle());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Equipement", id);
        repository.deleteById(id);
    }
}
