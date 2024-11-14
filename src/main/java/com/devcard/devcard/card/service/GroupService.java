package com.devcard.devcard.card.service;

import com.devcard.devcard.auth.entity.Member;
import com.devcard.devcard.card.dto.GroupResponseDto;
import com.devcard.devcard.card.entity.Card;
import com.devcard.devcard.card.entity.Group;
import com.devcard.devcard.card.repository.CardRepository;
import com.devcard.devcard.card.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final CardRepository cardRepository;

    public GroupService(GroupRepository groupRepository, CardRepository cardRepository) {
        this.groupRepository = groupRepository;
        this.cardRepository = cardRepository;
    }

    @Transactional
    public GroupResponseDto createGroup(String name, Member member) {
        Group group = new Group(name, member);
        group = groupRepository.save(group);

        return new GroupResponseDto(group.getId(), group.getName(), group.getCount());
    }

    public List<GroupResponseDto> getGroupsByMember(Member member) {
        List<Group> groups = groupRepository.findByMember(member);

        return groups.stream()
                .map(group -> new GroupResponseDto(group.getId(), group.getName(), group.getCount()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addCardToGroup(Long groupId, Long cardId, Member member) {
        Group group = groupRepository.findByIdAndMember(groupId, member)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 존재하지 않거나 접근 권한이 없습니다."));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 명함이 존재하지 않습니다."));

        group.addCard(card);
        groupRepository.save(group);
    }
}
