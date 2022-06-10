package org.fourstack.reactivecricinfo.playerinfoservice.unit;

import org.fourstack.reactivecricinfo.playerinfoservice.dao.PlayerProfileRepository;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.fourstack.reactivecricinfo.playerinfoservice.service.PlayerProfileServiceImpl;
import org.fourstack.reactivecricinfo.playerinfoservice.util.EntityGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import reactor.core.publisher.Mono;

@SpringBootTest
public class PlayerProfileServiceTest {

    PlayerProfile playerProfile = EntityGenerator.getPlayerProfile();
    PlayerInfoDTO playerInfoDTO = EntityGenerator.getPlayerInfoDTO();

    @Mock
    private PlayerProfileRepository playerRepository;

    @Mock
    private ConversionService profileToDtoConverter;

    @InjectMocks
    private PlayerProfileServiceImpl service;

    @Test
    public void testGetPlayerById() {
        String playerId = "VIR2022-6Y8-5P14-48SA-257223300";

        Mockito.when(playerRepository.findById(playerId))
                .thenReturn(Mono.just(playerProfile));

        Mockito.when(profileToDtoConverter.convert(playerProfile, PlayerInfoDTO.class))
                .thenReturn(playerInfoDTO);

        Mono<PlayerInfoDTO> playerDTO = service.getPlayerById(playerId);
        assert playerDTO != null;

        //verify the method calls
        Mockito.verify(playerRepository, Mockito.times(1))
                .findById(playerId);
    }
}
