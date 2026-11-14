package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.service.IPaiementService;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "Read-only payment operations; writes go through contracts")
public class PaiementController {
    private final IPaiementService service;

    @GetMapping
    @Operation(summary = "List payments")
    @ApiResponse(responseCode = "200", description = "List returned")
    public List<Paiement> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by id")
    @ApiResponse(responseCode = "200", description = "Payment found")
    @ApiResponse(responseCode = "404", description = "Payment not found")
    public Paiement findById(@Parameter(description = "Payment identifier") @PathVariable Long id) { return service.findById(id); }
}
