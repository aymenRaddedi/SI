package tn.esprit.autoloc.web.dto;

import java.time.LocalDate;
import tn.esprit.autoloc.domain.Maintenance;

public record MaintenanceDto(
        Long idMaintenance,
        LocalDate dateDebut,
        LocalDate dateFin,
        String description,
        Long vehiculeId,
        String immatriculationVehicule) {

    public static MaintenanceDto from(Maintenance maintenance) {
        return new MaintenanceDto(
                maintenance.getIdMaintenance(),
                maintenance.getDateDebut(),
                maintenance.getDateFin(),
                maintenance.getDescription(),
                maintenance.getVehicule().getIdVehicule(),
                maintenance.getVehicule().getImmatriculation());
    }
}
