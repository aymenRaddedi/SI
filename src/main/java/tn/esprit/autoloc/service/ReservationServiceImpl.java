package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository repository;

    private void validate(Reservation entity) {
        if (entity.getDateDebut() == null || entity.getDateFin() == null || entity.getDateFin().isBefore(entity.getDateDebut())) throw new IllegalArgumentException("Invalid Reservation data");
    }

    @Override
    public Reservation create(Reservation entity) {
        if (entity.getIdReservation() != null) throw new IllegalArgumentException("A new Reservation must not have an id");
        validate(entity);
        return repository.save(entity);
    }

    @Override
    public Reservation findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> findAll() { return repository.findAll(); }

    @Override
    public Reservation update(Long id, Reservation entity) {
        Reservation existing = findById(id);
        validate(entity);
        existing.setDateDebut(entity.getDateDebut());
        existing.setDateFin(entity.getDateFin());
        existing.setStatut(entity.getStatut());
        existing.setClient(entity.getClient());
        existing.setVehicule(entity.getVehicule());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Reservation", id);
        repository.deleteById(id);
    }
}
