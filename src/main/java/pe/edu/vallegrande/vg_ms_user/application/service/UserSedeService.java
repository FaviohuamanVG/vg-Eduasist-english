package pe.edu.vallegrande.vg_ms_user.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.vg_ms_user.domain.model.UserSede;
import pe.edu.vallegrande.vg_ms_user.infrastructure.exception.ResourceNotFoundException;
import pe.edu.vallegrande.vg_ms_user.infrastructure.repository.UserSedeRepository;
import pe.edu.vallegrande.vg_ms_user.infrastructure.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class UserSedeService {

    private final UserSedeRepository userSedeRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserSedeService(UserSedeRepository userSedeRepository, UserRepository userRepository) {
        this.userSedeRepository = userSedeRepository;
        this.userRepository = userRepository;
    }

    public List<UserSede> getAllUserSedes(String statusFilter) {
        if (statusFilter != null && !statusFilter.isEmpty()) {
            return userSedeRepository.findByStatus(statusFilter);
        } else {
            return userSedeRepository.findAll();
        }
    }

    public List<UserSede> getAllActiveUserSedes() {
        return userSedeRepository.findByStatus("Activo");
    }

    public UserSede getUserSedeById(String id) {
        Optional<UserSede> userSede = userSedeRepository.findById(id);
        if (userSede.isEmpty() || !"Activo".equals(userSede.get().getStatus())) {
            throw new ResourceNotFoundException("UserSede not found with id: " + id);
        }
        return userSede.get();
    }

    public UserSede createUserSede(UserSede userSede) {
        userRepository.findById(userSede.getUserId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("User not found with id: " + userSede.getUserId())))
                .map(user -> {
                    userSede.setRole(user.getRole());
                    return userSede; // Return userSede after setting the role
                }).block(); // Block to get the result, consider using reactive flow throughout if possible
        userSede.setStatus("Activo");
        return userSedeRepository.save(userSede);
    }

    public UserSede updateUserSede(String id, UserSede userSedeDetails) {
        UserSede userSede = userSedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserSede not found with id: " + id));

        userSede.setUserId(userSedeDetails.getUserId());
        userSede.setSedeId(userSedeDetails.getSedeId());
        userSede.setSortOrder(userSedeDetails.getSortOrder());
        // Role is set from User in create, might need to update if userId changes
        userRepository.findById(userSedeDetails.getUserId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("User not found with id: " + userSedeDetails.getUserId())))
                .map(user -> {
 userSede.setRole(user.getRole()); return userSede;}).block(); // Consider replacing block() with reactive chaining
        userSede.setObservations(userSedeDetails.getObservations());
        userSede.setSchedule(userSedeDetails.getSchedule());
        // Status is managed by deleteUserSede for logical deletion

		userSede.setAssignmentReason(userSedeDetails.getAssignmentReason());
		userSede.setAssignedAt(userSedeDetails.getAssignedAt());
		userSede.setActiveUntil(userSedeDetails.getActiveUntil());
		userSede.setResponsibilities(userSedeDetails.getResponsibilities());
        return userSedeRepository.save(userSede);
    }

    public void deleteUserSede(String id) {
        UserSede userSede = userSedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserSede not found with id: " + id));
        userSede.setStatus("Inactivo"); // Logical deletion
        userSedeRepository.save(userSede);
    }

    public UserSede activateUserSede(String id) {
        UserSede userSede = userSedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserSede not found with id: " + id));
        userSede.setStatus("Activo"); // Activate
        return userSedeRepository.save(userSede);
    }
}