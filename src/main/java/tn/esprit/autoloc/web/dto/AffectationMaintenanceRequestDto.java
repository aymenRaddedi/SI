package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AffectationMaintenanceRequestDto(
        @NotNull(message = "L'identifiant de maintenance est obligatoire")
        @Positive(message = "L'identifiant de maintenance doit être positif")
        Long maintenanceId,
        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        @Positive(message = "L'identifiant du véhicule doit être positif")
        Long vehiculeId) {
}
