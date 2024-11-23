package com.mate.album30.domain.album.service;

import com.mate.album30.domain.album.entity.Album;
import com.mate.album30.domain.album.entity.FavoriteAlbum;
import com.mate.album30.domain.album.repository.AlbumRepository;
import com.mate.album30.domain.album.repository.FavoriteAlbumRepository;
import com.mate.album30.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final FavoriteAlbumRepository favoriteAlbumRepository;

    // 앨범 디테일 조회 로직
    public Album getAlbumDetail(Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("앨범을 찾을 수 없습니다. ID: " + albumId));
    }

    // 앨범 찜/찜 해제
    public String toggleFavorite(Long userId, Long albumId) {
        Member member = getUserById(userId); // 사용자 조회 로직 필요
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("앨범을 찾을 수 없습니다. ID: " + albumId));

        Optional<FavoriteAlbum> favorite = favoriteAlbumRepository.findByMemberAndAlbum(member, album);

        if (favorite.isPresent()) {
            // 이미 찜한 경우 -> 찜 해제
            favoriteAlbumRepository.delete(favorite.get());
            return "앨범 찜 해제 완료!";
        } else {
            // 찜하지 않은 경우 -> 찜 추가
            FavoriteAlbum newFavorite = new FavoriteAlbum();
            newFavorite.setMember(member);
            newFavorite.setAlbum(album);
            favoriteAlbumRepository.save(newFavorite);
            return "앨범 찜 완료!";
        }
    }

    // 특정 사용자의 찜한 앨범 목록
    public List<Album> getFavoriteAlbums(Long userId) {
        Member member = getUserById(userId); // 사용자 조회 로직 필요
        List<FavoriteAlbum> favoriteAlbums = favoriteAlbumRepository.findAllByMember(member);
        return favoriteAlbums.stream()
                .map(FavoriteAlbum::getAlbum)
                .toList();
    }

    // 사용자 조회 로직 (임시)
    private Member getUserById(Long userId) {
        // 사용자 조회 로직 구현 필요
        throw new UnsupportedOperationException("사용자 조회 로직이 필요합니다.");
    }

}
