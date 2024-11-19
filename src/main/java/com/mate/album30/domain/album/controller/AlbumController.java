package com.mate.album30.domain.album.controller;

import com.mate.album30.domain.album.entity.Album;
import com.mate.album30.domain.album.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
