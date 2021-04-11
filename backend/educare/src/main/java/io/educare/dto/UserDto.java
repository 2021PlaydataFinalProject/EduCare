package io.educare.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

   @NotNull
   @Size(min = 8, max = 50)
   private String username;
   
   // 해당 필드는 오직 쓰려는 경우(deserialize)에만 접근이 허용
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   @NotNull
   @Size(min = 8, max = 50)
   private String password;

   @NotNull
   @Size(min = 2, max = 50)
   private String userRealname;
   
   @NotNull
   @Size(min = 2, max = 50)
   private String phoneNumber;
   
   @Size(min = 2, max = 100)
   private String userImage;
   
   @NotNull
   @Size(min = 2, max = 50)
   private String role;
   
}