package com.kanzariya.chatsphere.records;

public record LoginResponseDTO(String status, String jwtToken,int id,String fullName,String role) {

}
