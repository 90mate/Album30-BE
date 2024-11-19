package com.mate.album30.domain.album.service;

import com.mate.album30.domain.album.entity.Album;
import com.mate.album30.domain.album.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    // 앨범 디테일 조회 로직
    public Album getAlbumDetail(Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("앨범을 찾을 수 없습니다. ID: " + albumId));
    }
}
