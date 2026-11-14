package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {
    private final IMaintenanceRepository repository;

    private void validate(Maintenance entity) {
        if (entity.getDateDebut() == null || (entity.getDateFin() != null && entity.getDateFin().isBefore(entity.getDateDebut()))) throw new IllegalArgumentException("Invalid Maintenance data");
    }

    @Override
    public Maintenance create(Maintenance entity) {
        if (entity.getIdMaintenance() != null) throw new IllegalArgumentException("A new Maintenance must not have an id");
        validate(entity);
        return repository.save(entity);
    }

    @Override
    public Maintenance findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() { return repository.findAll(); }

    @Override
    public Maintenance update(Long id, Maintenance entity) {
        Maintenance existing = findById(id);
        validate(entity);
        existing.setDateDebut(entity.getDateDebut());
        existing.setDateFin(entity.getDateFin());
        existing.setDescription(entity.getDescription());
        existing.setVehicule(entity.getVehicule());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Maintenance", id);
        repository.deleteById(id);
    }
}
