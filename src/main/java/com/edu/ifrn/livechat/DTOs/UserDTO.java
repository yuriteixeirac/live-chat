package com.edu.ifrn.livechat.DTOs;

import com.edu.ifrn.livechat.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;

    public UserDTO(User user) {
        this.username = user.getUsername();
    }
}
