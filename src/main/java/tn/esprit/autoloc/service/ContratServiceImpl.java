package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {
    private final IContratRepository repository;

    private void validate(Contrat entity) {
        if (entity.getDateSignature() == null || entity.getMontantTotal() == null || entity.getMontantTotal().signum() < 0) throw new IllegalArgumentException("Invalid Contrat data");
    }

    @Override
    public Contrat create(Contrat entity) {
        if (entity.getIdContrat() != null) throw new IllegalArgumentException("A new Contrat must not have an id");
        validate(entity);
        return repository.save(entity);
    }

    @Override
    public Contrat findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() { return repository.findAll(); }

    @Override
    public Contrat update(Long id, Contrat entity) {
        Contrat existing = findById(id);
        validate(entity);
        existing.setDateSignature(entity.getDateSignature());
        existing.setMontantTotal(entity.getMontantTotal());
        existing.setValide(entity.isValide());
        existing.setReservation(entity.getReservation());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Contrat", id);
        repository.deleteById(id);
    }
}

