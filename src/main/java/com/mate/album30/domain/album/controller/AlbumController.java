package com.mate.album30.domain.album.controller;

import com.mate.album30.domain.album.entity.Album;
import com.mate.album30.domain.album.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    // Album Detail 조회
    @GetMapping("/detail/{id}")
    public ResponseEntity<Album> getAlbumDetail(@PathVariable("id") Long albumId){
        Album albumDetail = albumService.getAlbumDetail(albumId);
        return ResponseEntity.ok(albumDetail);
    }

    // 앨범 찜/찜 해제
    @PostMapping("/favorite/{albumId}")
    public ResponseEntity<String> toggleFavorite(
            @RequestParam("memberId") Long memberId,
            @PathVariable("albumId") Long albumId) {
        String message = albumService.toggleFavorite(memberId, albumId);
        return ResponseEntity.ok(message);
    }

    // 특정 사용자의 찜한 앨범 목록 조회
    @GetMapping("/favorites")
    public ResponseEntity<List<Album>> getFavoriteAlbums(@RequestParam("memberId") Long memberId) {
        List<Album> favoriteAlbums = albumService.getFavoriteAlbums(memberId);
        return ResponseEntity.ok(favoriteAlbums);
    }

}
