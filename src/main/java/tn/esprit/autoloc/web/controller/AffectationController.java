package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.AffectationEquipementRequestDto;
import tn.esprit.autoloc.web.dto.AffectationMaintenanceRequestDto;
import tn.esprit.autoloc.web.dto.MaintenanceDto;
import tn.esprit.autoloc.web.dto.VehiculeDto;

@RestController
@RequestMapping("/affectations")
@RequiredArgsConstructor
@Tag(name = "Affectations", description = "Assign maintenance and equipment to vehicles")
public class AffectationController {
    private final IVehiculeService vehiculeService;

    @PostMapping("/maintenance")
    @Operation(summary = "Affecter une maintenance a un vehicule")
    @ApiResponse(responseCode = "200", description = "Maintenance affectee; DTO retourne")
    @ApiResponse(responseCode = "400", description = "Identifiants invalides")
    @ApiResponse(responseCode = "404", description = "Vehicule ou maintenance introuvable")
    public MaintenanceDto affecterMaintenance(@Valid @RequestBody AffectationMaintenanceRequestDto request) {
        return vehiculeService.affecterMaintenance(request);
    }

    @PostMapping("/equipement")
    @Operation(summary = "Affecter un equipement a un vehicule")
    @ApiResponse(responseCode = "200", description = "Vehicule mis a jour; DTO retourne")
    @ApiResponse(responseCode = "400", description = "Identifiants invalides")
    @ApiResponse(responseCode = "404", description = "Vehicule ou equipement introuvable")
    public VehiculeDto affecterEquipement(@Valid @RequestBody AffectationEquipementRequestDto request) {
        return vehiculeService.affecterEquipement(request);
    }
}
