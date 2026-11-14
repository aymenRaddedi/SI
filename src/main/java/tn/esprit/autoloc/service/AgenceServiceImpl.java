package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository repository;

    private void validate(Agence entity) {
        if (entity.getNom() == null || entity.getNom().isBlank() || entity.getVille() == null || entity.getVille().isBlank() || entity.getAdresse() == null || entity.getAdresse().isBlank()) throw new IllegalArgumentException("Invalid Agence data");
    }

    @Override
    public Agence create(Agence entity) {
        if (entity.getIdAgence() != null) throw new IllegalArgumentException("A new Agence must not have an id");
        validate(entity);
        return repository.save(entity);
    }

    @Override
    public Agence findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    public List<Agence> findAll() { return repository.findAll(); }

    @Override
    public Agence update(Long id, Agence entity) {
        Agence existing = findById(id);
        validate(entity);
        existing.setNom(entity.getNom());
        existing.setVille(entity.getVille());
        existing.setAdresse(entity.getAdresse());
        existing.setTelephone(entity.getTelephone());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Agence", id);
        repository.deleteById(id);
    }
}
