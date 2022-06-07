package org.fourstack.reactivecricinfo.battinginfoservice.controller;

import org.fourstack.reactivecricinfo.battinginfoservice.dto.BattingInfoDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * RestController class which acts for query methods (GET..)
 * It is responsible to expose only query related methods,
 * which doesn't change state of Object.
 */
@RestController
@RequestMapping("/api/v1/batting-info")
public class BattingInfoQueryController {
}
