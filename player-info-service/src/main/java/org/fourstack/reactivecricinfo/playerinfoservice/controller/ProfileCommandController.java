package org.fourstack.reactivecricinfo.playerinfoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.service.PlayerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * RestController class which acts for commands (PUT, POST, PATCH, DELETE etc.).
 * It only exposes the methods which are responsible for change of state of Object.
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ProfileCommandController {

    @Autowired
    private PlayerProfileService playerService;

    @Operation(
            description = "Create Player Profile",
            summary = "API to create Player profile information.",
            tags = "PLAYER-PROFILE_SERVICE :: COMMAND METHODS",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = PlayerInfoDTO.class)
                    ),
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Player profile created successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = PlayerInfoDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BAD REQUEST",
                            content = @Content()
                    )
            }
    )
    @PostMapping("/player")
    public Mono<PlayerInfoDTO> createPlayerProfile(@RequestBody PlayerInfoDTO profileDto) {

        log.info("Received payload :: " + profileDto);

        /*
        either we need to explicitly subscribe or do the return.
        If we return then Web flux itself will subscribe for the data
         */
        return playerService.createPlayerProfile(profileDto);
    }
}
