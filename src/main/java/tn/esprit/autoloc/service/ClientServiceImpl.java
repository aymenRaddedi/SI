package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {
    private final IClientRepository repository;

    private void validate(Client entity) {
        if (entity.getNom() == null || entity.getNom().isBlank() || entity.getPrenom() == null || entity.getPrenom().isBlank() || entity.getEmail() == null || entity.getEmail().isBlank() || entity.getNumPermis() == null || entity.getNumPermis().isBlank() || entity.getDateInscription() == null) throw new IllegalArgumentException("Invalid Client data");
    }

    @Override
    public Client create(Client entity) {
        if (entity.getIdClient() != null) throw new IllegalArgumentException("A new Client must not have an id");
        validate(entity);
        return repository.save(entity);
    }

    @Override
    public Client findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Override
    public List<Client> findAll() { return repository.findAll(); }

    @Override
    public Client update(Long id, Client entity) {
        Client existing = findById(id);
        validate(entity);
        existing.setNom(entity.getNom());
        existing.setPrenom(entity.getPrenom());
        existing.setEmail(entity.getEmail());
        existing.setTelephone(entity.getTelephone());
        existing.setNumPermis(entity.getNumPermis());
        existing.setDateInscription(entity.getDateInscription());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Client", id);
        repository.deleteById(id);
    }
}
