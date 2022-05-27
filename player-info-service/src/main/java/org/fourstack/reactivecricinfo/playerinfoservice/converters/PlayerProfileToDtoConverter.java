package org.fourstack.reactivecricinfo.playerinfoservice.converters;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.fourstack.reactivecricinfo.playerinfoservice.model.common.MultiMediaDocument;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PlayerProfileToDtoConverter implements
        Converter<Mono<PlayerProfile>, Mono<PlayerInfoDTO>> {

    /**
     * Method which converts Mono {@link PlayerProfile} DAO Object
     * to Mono {@link PlayerInfoDTO} DTO Object.
     * @param source
     * @return
     */
    @Override
    public Mono<PlayerInfoDTO> convert(Mono<PlayerProfile> source) {
        return source.map(ProfileToDtoConverter :: convert);
    }
}
