package tn.esprit.autoloc.web.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import tn.esprit.autoloc.domain.Vehicule;

public record VehiculeDto(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        BigDecimal tarifJournalier,
        Set<Long> equipementIds) {

    public static VehiculeDto from(Vehicule vehicule) {
        return new VehiculeDto(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getTarifJournalier(),
                vehicule.getEquipements().stream()
                        .map(equipement -> equipement.getIdEquipement())
                        .collect(Collectors.toUnmodifiableSet()));
    }
}
