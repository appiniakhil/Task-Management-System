package com.taskmanagementsystem.task_management_system.dto;

import com.taskmanagementsystem.task_management_system.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    public static UserDTO fromEntity(User user) {
        if (user == null)
            return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
