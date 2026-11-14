package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AffectationEquipementRequestDto(
        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        @Positive(message = "L'identifiant du véhicule doit être positif")
        Long vehiculeId,
        @NotNull(message = "L'identifiant de l'équipement est obligatoire")
        @Positive(message = "L'identifiant de l'équipement doit être positif")
        Long equipementId) {
}
