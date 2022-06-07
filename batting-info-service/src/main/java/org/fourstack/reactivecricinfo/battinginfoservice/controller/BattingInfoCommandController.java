package org.fourstack.reactivecricinfo.battinginfoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.fourstack.reactivecricinfo.battinginfoservice.dto.BattingInfoDTO;
import org.fourstack.reactivecricinfo.battinginfoservice.service.BattingInfoService;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * RestController class which acts for the commands POST, PUT, DELETE and PATCH.
 * It exposes the methods which are responsible for the change of state of Object.
 */
@RestController
@RequestMapping("/api/v1/batting-info")
public class BattingInfoCommandController {

    @Autowired
    private BattingInfoService service;

    @Operation(
            description = "Create Batting Info",
            summary = "API method to Create Batting Info",
            method = "POST",
            tags = "BATTING-INFO-SERVICE :: COMMAND METHODS",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = BattingInfoDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    schema = @Schema(implementation = BattingInfoDTO.class)
                            ),
                            description = "BattingInfo Object created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = {@Content()},
                            description = "Bad Request"
                    )
            }
    )
    @PostMapping
    public Mono<BattingInfoDTO> createBattingInfo(@RequestBody BattingInfoDTO dto) {
        return service.createBattingInfo(dto);
    }
}
