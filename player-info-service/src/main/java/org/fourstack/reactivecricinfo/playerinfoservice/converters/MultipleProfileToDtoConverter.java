package org.fourstack.reactivecricinfo.playerinfoservice.converters;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.fourstack.reactivecricinfo.playerinfoservice.model.common.MultiMediaDocument;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class MultipleProfileToDtoConverter implements
        Converter<Flux<PlayerProfile>, Flux<PlayerInfoDTO>> {
    @Override
    public Flux<PlayerInfoDTO> convert(Flux<PlayerProfile> source) {
        return source.map(ProfileToDtoConverter :: convert);
    }
}
