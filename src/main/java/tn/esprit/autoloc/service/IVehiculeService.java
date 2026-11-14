package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.AffectationEquipementRequestDto;
import tn.esprit.autoloc.web.dto.AffectationMaintenanceRequestDto;
import tn.esprit.autoloc.web.dto.MaintenanceDto;
import tn.esprit.autoloc.web.dto.VehiculeDto;

public interface IVehiculeService {
    Vehicule create(Vehicule entity);
    Vehicule findById(Long id);
    List<Vehicule> findAll();
    Vehicule update(Long id, Vehicule entity);
    void deleteById(Long id);
    MaintenanceDto affecterMaintenance(AffectationMaintenanceRequestDto request);
    VehiculeDto affecterEquipement(AffectationEquipementRequestDto request);
}
