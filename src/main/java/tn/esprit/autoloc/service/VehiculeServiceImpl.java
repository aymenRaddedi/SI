package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository repository;

    private void validate(Vehicule entity) {
        if (entity.getImmatriculation() == null || entity.getImmatriculation().isBlank() || entity.getMarque() == null || entity.getMarque().isBlank() || entity.getModele() == null || entity.getModele().isBlank() || entity.getCategorie() == null || entity.getStatut() == null || entity.getTarifJournalier() == null || entity.getTarifJournalier().signum() < 0) throw new IllegalArgumentException("Invalid Vehicule data");
    }

    @Override
    public Vehicule create(Vehicule entity) {
        if (entity.getIdVehicule() != null) throw new IllegalArgumentException("A new Vehicule must not have an id");
        validate(entity);
        return repository.save(entity);
    }

    @Override
    public Vehicule findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() { return repository.findAll(); }

    @Override
    public Vehicule update(Long id, Vehicule entity) {
        Vehicule existing = findById(id);
        validate(entity);
        existing.setImmatriculation(entity.getImmatriculation());
        existing.setMarque(entity.getMarque());
        existing.setModele(entity.getModele());
        existing.setCategorie(entity.getCategorie());
        existing.setTarifJournalier(entity.getTarifJournalier());
        existing.setStatut(entity.getStatut());
        existing.setAgence(entity.getAgence());
        existing.setEquipements(entity.getEquipements());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Vehicule", id);
        repository.deleteById(id);
    }
}
