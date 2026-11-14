package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Reservation;

public interface IReservationService {
    Reservation create(Reservation entity);
    Reservation findById(Long id);
    List<Reservation> findAll();
    Reservation update(Long id, Reservation entity);
    void deleteById(Long id);
}
