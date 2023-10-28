package app.web.ishare.dto;

import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {
    
    @Nonnull
    private Integer id;

    @Nonnull
    private String roleName;
}
