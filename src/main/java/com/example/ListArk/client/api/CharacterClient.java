package com.example.ListArk.client.api;

import com.example.ListArk.client.util.WebClientHelper;
import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.character.SiblingCharacterDto;
import com.example.ListArk.client.util.RetryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.ListArk.client.util.WebClientHelper.*;

@Service
@RequiredArgsConstructor
public class CharacterClient {

    private final WebClient webClient;

    public Mono<List<SiblingCharacterDto>> getCharacterSiblings(String name) {
        return WebClientHelper.getList(
                webClient,
                "/characters/" + name + "/siblings",
                SiblingCharacterDto.class,
                name);
    }
}
